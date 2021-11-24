package podium.ppi.lib.jar;
import static com.google.common.base.Preconditions.checkArgument;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.annotation.Nullable;
import javax.sql.DataSource;

import org.apache.beam.sdk.annotations.Experimental;
import org.apache.beam.sdk.options.PipelineOptions;
import org.apache.beam.sdk.transforms.DoFn;
import org.apache.beam.sdk.transforms.PTransform;
import org.apache.beam.sdk.transforms.ParDo;
import org.apache.beam.sdk.transforms.display.DisplayData;
import org.apache.beam.sdk.values.PCollection;
import org.apache.beam.sdk.values.PCollectionTuple;
import org.apache.beam.sdk.values.TupleTag;
import org.apache.beam.sdk.values.TupleTagList;
import org.apache.commons.dbcp2.BasicDataSource;

import com.google.auto.value.AutoValue;

@Experimental
public class CustomJdbcIOWrite {

  /**
   * Write data to a JDBC datasource.
   *
   * @param <T> Type of the data to be written.
   */
  public static <T> Write<T> write() {
    return new AutoValue_JdbcIO_Write.Builder<T>().build();
  }

  private CustomJdbcIOWrite() {}

  /**
   * An interface used by {@link CustomJdbcIOWrite.Read} for converting each row of the {@link ResultSet} into
   * an element of the resulting {@link PCollection}.
   */
  public interface RowMapper<T> extends Serializable {
    T mapRow(ResultSet resultSet) throws Exception;
  }

  /**
   * A POJO describing a {@link DataSource}, either providing directly a {@link DataSource} or all
   * properties allowing to create a {@link DataSource}.
   */
  @AutoValue
  public abstract static class DataSourceConfiguration implements Serializable {
    @Nullable abstract String getDriverClassName();
    @Nullable abstract String getUrl();
    @Nullable abstract String getUsername();
    @Nullable abstract String getPassword();
    @Nullable abstract String getConnectionProperties();
    @Nullable abstract DataSource getDataSource();

    abstract Builder builder();

    @AutoValue.Builder
    abstract static class Builder {
      abstract Builder setDriverClassName(String driverClassName);
      abstract Builder setUrl(String url);
      abstract Builder setUsername(String username);
      abstract Builder setPassword(String password);
      abstract Builder setConnectionProperties(String connectionProperties);
      abstract Builder setDataSource(DataSource dataSource);
      abstract DataSourceConfiguration build();
    }

    public static DataSourceConfiguration create(DataSource dataSource) {
      checkArgument(dataSource != null, "DataSourceConfiguration.create(dataSource) called with "
          + "null data source");
      checkArgument(dataSource instanceof Serializable,
          "DataSourceConfiguration.create(dataSource) called with a dataSource not Serializable");
      return new AutoValue_JdbcIO_DataSourceConfiguration.Builder()
          .setDataSource(dataSource)
          .build();
    }

    public static DataSourceConfiguration create(String driverClassName, String url) {
      checkArgument(driverClassName != null,
          "DataSourceConfiguration.create(driverClassName, url) called with null driverClassName");
      checkArgument(url != null,
          "DataSourceConfiguration.create(driverClassName, url) called with null url");
      return new AutoValue_JdbcIO_DataSourceConfiguration.Builder()
          .setDriverClassName(driverClassName)
          .setUrl(url)
          .build();
    }

    public DataSourceConfiguration withUsername(String username) {
      return builder().setUsername(username).build();
    }

    public DataSourceConfiguration withPassword(String password) {
      return builder().setPassword(password).build();
    }

    /**
     * Sets the connection properties passed to driver.connect(...).
     * Format of the string must be [propertyName=property;]*
     *
     * <p>NOTE - The "user" and "password" properties can be add via {@link #withUsername(String)},
     * {@link #withPassword(String)}, so they do not need to be included here.
     */
    public DataSourceConfiguration withConnectionProperties(String connectionProperties) {
      checkArgument(connectionProperties != null, "DataSourceConfiguration.create(driver, url)"
          + ".withConnectionProperties(connectionProperties) "
          + "called with null connectionProperties");
      return builder().setConnectionProperties(connectionProperties).build();
    }

    private void populateDisplayData(DisplayData.Builder builder) {
      if (getDataSource() != null) {
        builder.addIfNotNull(DisplayData.item("dataSource", getDataSource().getClass().getName()));
      } else {
        builder.addIfNotNull(DisplayData.item("jdbcDriverClassName", getDriverClassName()));
        builder.addIfNotNull(DisplayData.item("jdbcUrl", getUrl()));
        builder.addIfNotNull(DisplayData.item("username", getUsername()));
      }
    }

    DataSource buildDatasource() throws Exception{
      if (getDataSource() != null) {
        return getDataSource();
      } else {
        BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setDriverClassName(getDriverClassName());
        basicDataSource.setUrl(getUrl());
        basicDataSource.setUsername(getUsername());
        basicDataSource.setPassword(getPassword());
        if (getConnectionProperties() != null) {
          basicDataSource.setConnectionProperties(getConnectionProperties());
        }
        return basicDataSource;
      }
    }

  }

  /**
   * An interface used by the JdbcIO Write to set the parameters of the {@link PreparedStatement}
   * used to setParameters into the database.
   */
  public interface StatementPreparator extends Serializable {
    void setParameters(PreparedStatement preparedStatement) throws Exception;
  }

  public interface PreparedStatementSetter<T> extends Serializable {
    void setParameters(T element, PreparedStatement preparedStatement) throws Exception;
  }

  /** A {@link PTransform} to write to a JDBC datasource. */
  @AutoValue
  public abstract static class Write<T> extends PTransform<PCollection<T>, PCollectionTuple> {
	  final TupleTag<Stock> tupletagTest = new TupleTag<Stock>() {} ;
    @Nullable abstract DataSourceConfiguration getDataSourceConfiguration();
    @Nullable abstract String getStatement();
    @Nullable abstract PreparedStatementSetter<T> getPreparedStatementSetter();
    @Nullable
	abstract TupleTag<T> getValidTupleTag();

	@Nullable
	abstract TupleTag<T> getInvalidTupleTag();

    abstract Builder<T> toBuilder();

    @AutoValue.Builder
    abstract static class Builder<T> {
      abstract Builder<T> setDataSourceConfiguration(DataSourceConfiguration config);
      abstract Builder<T> setStatement(String statement);
      abstract Builder<T> setPreparedStatementSetter(PreparedStatementSetter<T> setter);
      abstract Builder<T> setValidTupleTag(TupleTag<T> validtag);
      abstract Builder<T> setInvalidTupleTag(TupleTag<T> inValidtag);

      abstract Write<T> build();
    }

    public Write<T> withDataSourceConfiguration(DataSourceConfiguration config) {
      return toBuilder().setDataSourceConfiguration(config).build();
    }
    public Write<T> withStatement(String statement) {
      return toBuilder().setStatement(statement).build();
    }
    public Write<T> withPreparedStatementSetter(PreparedStatementSetter<T> setter) {
      return toBuilder().setPreparedStatementSetter(setter).build();
    }
    public Write<T> withValidTag(TupleTag<T> validtag) {
      return toBuilder().setValidTupleTag(validtag).build();
    }
    public Write<T> withInValidTag(TupleTag<T> inValidtag) {
        return toBuilder().setInvalidTupleTag(inValidtag).build();
    }

    @Override
    public PCollectionTuple expand(PCollection<T> input) {
    	return input.apply(ParDo.of(new WriteFn<T>(this)).
    			withOutputTags(this.getValidTupleTag(), TupleTagList.of(this.getInvalidTupleTag()).and(tupletagTest)));
    }

    @Override
    public void validate(PipelineOptions options) {
      checkArgument(getDataSourceConfiguration() != null,
          "JdbcIO.write() requires a configuration to be set via "
              + ".withDataSourceConfiguration(configuration)");
      checkArgument(getStatement() != null,
          "JdbcIO.write() requires a statement to be set via .withStatement(statement)");
      checkArgument(getPreparedStatementSetter() != null,
          "JdbcIO.write() requires a preparedStatementSetter to be set via "
              + ".withPreparedStatementSetter(preparedStatementSetter)");
    }

    private static class WriteFn<T> extends DoFn<T, T> {
      private static final int DEFAULT_BATCH_SIZE = 1;

      private final Write<T> spec;

      private DataSource dataSource;
      private Connection connection;
      private PreparedStatement preparedStatement;
      private TupleTag<T> validTupleTag;
      private TupleTag<T> inValidTupleTag;
      private int batchCount;

      public WriteFn(Write<T> spec) {
        this.spec = spec;
      }

      @Setup
      public void setup() throws Exception {
        dataSource = spec.getDataSourceConfiguration().buildDatasource();
        connection = dataSource.getConnection();
        connection.setAutoCommit(false);
        preparedStatement = connection.prepareStatement(spec.getStatement());
        validTupleTag = spec.getValidTupleTag();
        inValidTupleTag = spec.getInvalidTupleTag();
      }

      @StartBundle
      public void startBundle() {
        batchCount = 0;
      }
      
      @ProcessElement
      public void processElement(@Element T record, MultiOutputReceiver out) throws Exception {
        preparedStatement.clearParameters();
        spec.getPreparedStatementSetter().setParameters(record, preparedStatement);
        preparedStatement.addBatch();

        batchCount++;

        if (batchCount >= DEFAULT_BATCH_SIZE) {
        	if (batchCount > 0) {
                try {
      			preparedStatement.executeBatch();
      			connection.commit();
      			out.get(validTupleTag).output(record);
      		} catch (SQLException e1) {
      			//TODO add logger
      			out.get(inValidTupleTag).output(record);
      		}
                batchCount = 0;
              }
        }
      }

      @FinishBundle
      public void finishBundle() throws Exception {
        executeBatch();
      }

      private void executeBatch() {
        
      }

      @Teardown
      public void teardown() throws Exception {
        try {
          if (preparedStatement != null) {
            preparedStatement.close();
          }
        } finally {
          if (connection != null) {
            connection.close();
          }
          if (dataSource instanceof AutoCloseable) {
            ((AutoCloseable) dataSource).close();
          }
        }
      }
    }
  }
}
