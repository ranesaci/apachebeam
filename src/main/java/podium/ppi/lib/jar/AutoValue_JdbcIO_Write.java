package podium.ppi.lib.jar;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;

import org.apache.beam.sdk.values.TupleTag;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_JdbcIO_Write<T> extends CustomJdbcIOWrite.Write<T> {

  private final CustomJdbcIOWrite.DataSourceConfiguration dataSourceConfiguration;
  private final String statement;
  private final CustomJdbcIOWrite.PreparedStatementSetter<T> preparedStatementSetter;
  private final TupleTag<T> validTupleTag;
  private final TupleTag<T> inValidTupleTag;

  private AutoValue_JdbcIO_Write(
      @Nullable CustomJdbcIOWrite.DataSourceConfiguration dataSourceConfiguration,
      @Nullable String statement,
      @Nullable CustomJdbcIOWrite.PreparedStatementSetter<T> preparedStatementSetter,
      @Nullable TupleTag<T> validTupleTag,
      @Nullable TupleTag<T> inValidTupleTag) {
    this.dataSourceConfiguration = dataSourceConfiguration;
    this.statement = statement;
    this.preparedStatementSetter = preparedStatementSetter;
    this.validTupleTag = validTupleTag;
    this.inValidTupleTag = inValidTupleTag;
  }

  @Nullable
  @Override
  CustomJdbcIOWrite.DataSourceConfiguration getDataSourceConfiguration() {
    return dataSourceConfiguration;
  }

  @Nullable
  @Override
  String getStatement() {
    return statement;
  }

  @Nullable
  @Override
  CustomJdbcIOWrite.PreparedStatementSetter<T> getPreparedStatementSetter() {
    return preparedStatementSetter;
  }
  
  @Nullable
  @Override
  TupleTag<T> getValidTupleTag() {
  	return validTupleTag;
  }

  @Nullable
  @Override
  TupleTag<T> getInvalidTupleTag() {
  	return inValidTupleTag;
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof CustomJdbcIOWrite.Write) {
    	CustomJdbcIOWrite.Write<?> that = (CustomJdbcIOWrite.Write<?>) o;
      return ((this.dataSourceConfiguration == null) ? (that.getDataSourceConfiguration() == null) : this.dataSourceConfiguration.equals(that.getDataSourceConfiguration()))
           && ((this.statement == null) ? (that.getStatement() == null) : this.statement.equals(that.getStatement()))
           && ((this.preparedStatementSetter == null) ? (that.getPreparedStatementSetter() == null) : this.preparedStatementSetter.equals(that.getPreparedStatementSetter()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (dataSourceConfiguration == null) ? 0 : this.dataSourceConfiguration.hashCode();
    h *= 1000003;
    h ^= (statement == null) ? 0 : this.statement.hashCode();
    h *= 1000003;
    h ^= (preparedStatementSetter == null) ? 0 : this.preparedStatementSetter.hashCode();
    return h;
  }

  @Override
  CustomJdbcIOWrite.Write.Builder<T> toBuilder() {
    return new Builder<T>(this);
  }

  static final class Builder<T> extends CustomJdbcIOWrite.Write.Builder<T> {
    private CustomJdbcIOWrite.DataSourceConfiguration dataSourceConfiguration;
    private String statement;
    private CustomJdbcIOWrite.PreparedStatementSetter<T> preparedStatementSetter;
    private TupleTag<T> validTupleTag;
    private TupleTag<T> inValidTupleTag;
    
    Builder() {
    }
    private Builder(CustomJdbcIOWrite.Write<T> source) {
      this.dataSourceConfiguration = source.getDataSourceConfiguration();
      this.statement = source.getStatement();
      this.preparedStatementSetter = source.getPreparedStatementSetter();
      this.validTupleTag = source.getValidTupleTag();
      this.inValidTupleTag = source.getInvalidTupleTag();
    }
    @Override
    CustomJdbcIOWrite.Write.Builder<T> setDataSourceConfiguration(@Nullable CustomJdbcIOWrite.DataSourceConfiguration dataSourceConfiguration) {
      this.dataSourceConfiguration = dataSourceConfiguration;
      return this;
    }
    @Override
    CustomJdbcIOWrite.Write.Builder<T> setStatement(@Nullable String statement) {
      this.statement = statement;
      return this;
    }
    @Override
    CustomJdbcIOWrite.Write.Builder<T> setPreparedStatementSetter(@Nullable CustomJdbcIOWrite.PreparedStatementSetter<T> preparedStatementSetter) {
      this.preparedStatementSetter = preparedStatementSetter;
      return this;
    }
    @Override
	CustomJdbcIOWrite.Write.Builder<T> setValidTupleTag(TupleTag<T> validtag) {
		this.validTupleTag = validtag;
		return this;
	}
	@Override
	CustomJdbcIOWrite.Write.Builder<T> setInvalidTupleTag(TupleTag<T> inValidtag) {
		this.inValidTupleTag = inValidtag;
		return this;
	}
    @Override
    CustomJdbcIOWrite.Write<T> build() {
      return new AutoValue_JdbcIO_Write<T>(
          this.dataSourceConfiguration,
          this.statement,
          this.preparedStatementSetter,
          this.validTupleTag,
          this.inValidTupleTag);
    }
	
  }

}
