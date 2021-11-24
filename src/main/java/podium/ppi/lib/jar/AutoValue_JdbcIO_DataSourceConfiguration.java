package podium.ppi.lib.jar;
import javax.annotation.Nullable;
import javax.annotation.processing.Generated;
import javax.sql.DataSource;

import org.apache.beam.sdk.io.jdbc.JdbcIO;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
 final class AutoValue_JdbcIO_DataSourceConfiguration extends CustomJdbcIOWrite.DataSourceConfiguration {

  private final String driverClassName;
  private final String url;
  private final String username;
  private final String password;
  private final String connectionProperties;
  private final DataSource dataSource;

  private AutoValue_JdbcIO_DataSourceConfiguration(
      @Nullable String driverClassName,
      @Nullable String url,
      @Nullable String username,
      @Nullable String password,
      @Nullable String connectionProperties,
      @Nullable DataSource dataSource) {
    this.driverClassName = driverClassName;
    this.url = url;
    this.username = username;
    this.password = password;
    this.connectionProperties = connectionProperties;
    this.dataSource = dataSource;
  }

  @Nullable
  @Override
  String getDriverClassName() {
    return driverClassName;
  }

  @Nullable
  @Override
  String getUrl() {
    return url;
  }

  @Nullable
  @Override
  String getUsername() {
    return username;
  }

  @Nullable
  @Override
  String getPassword() {
    return password;
  }

  @Nullable
  @Override
  String getConnectionProperties() {
    return connectionProperties;
  }

  @Nullable
  @Override
  DataSource getDataSource() {
    return dataSource;
  }

  @Override
  public String toString() {
    return "DataSourceConfiguration{"
        + "driverClassName=" + driverClassName + ", "
        + "url=" + url + ", "
        + "username=" + username + ", "
        + "password=" + password + ", "
        + "connectionProperties=" + connectionProperties + ", "
        + "dataSource=" + dataSource
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof CustomJdbcIOWrite.DataSourceConfiguration) {
    	CustomJdbcIOWrite.DataSourceConfiguration that = (CustomJdbcIOWrite.DataSourceConfiguration) o;
      return ((this.driverClassName == null) ? (that.getDriverClassName() == null) : this.driverClassName.equals(that.getDriverClassName()))
           && ((this.url == null) ? (that.getUrl() == null) : this.url.equals(that.getUrl()))
           && ((this.username == null) ? (that.getUsername() == null) : this.username.equals(that.getUsername()))
           && ((this.password == null) ? (that.getPassword() == null) : this.password.equals(that.getPassword()))
           && ((this.connectionProperties == null) ? (that.getConnectionProperties() == null) : this.connectionProperties.equals(that.getConnectionProperties()))
           && ((this.dataSource == null) ? (that.getDataSource() == null) : this.dataSource.equals(that.getDataSource()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h = 1;
    h *= 1000003;
    h ^= (driverClassName == null) ? 0 : this.driverClassName.hashCode();
    h *= 1000003;
    h ^= (url == null) ? 0 : this.url.hashCode();
    h *= 1000003;
    h ^= (username == null) ? 0 : this.username.hashCode();
    h *= 1000003;
    h ^= (password == null) ? 0 : this.password.hashCode();
    h *= 1000003;
    h ^= (connectionProperties == null) ? 0 : this.connectionProperties.hashCode();
    h *= 1000003;
    h ^= (dataSource == null) ? 0 : this.dataSource.hashCode();
    return h;
  }

  @Override
  CustomJdbcIOWrite.DataSourceConfiguration.Builder builder() {
    return new Builder(this);
  }

  static final class Builder extends CustomJdbcIOWrite.DataSourceConfiguration.Builder {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String connectionProperties;
    private DataSource dataSource;
    Builder() {
    }
    private Builder(CustomJdbcIOWrite.DataSourceConfiguration source) {
      this.driverClassName = source.getDriverClassName();
      this.url = source.getUrl();
      this.username = source.getUsername();
      this.password = source.getPassword();
      this.connectionProperties = source.getConnectionProperties();
      this.dataSource = source.getDataSource();
    }
    @Override
    CustomJdbcIOWrite.DataSourceConfiguration.Builder setDriverClassName(@Nullable String driverClassName) {
      this.driverClassName = driverClassName;
      return this;
    }
    @Override
    CustomJdbcIOWrite.DataSourceConfiguration.Builder setUrl(@Nullable String url) {
      this.url = url;
      return this;
    }
    @Override
    CustomJdbcIOWrite.DataSourceConfiguration.Builder setUsername(@Nullable String username) {
      this.username = username;
      return this;
    }
    @Override
    CustomJdbcIOWrite.DataSourceConfiguration.Builder setPassword(@Nullable String password) {
      this.password = password;
      return this;
    }
    @Override
    CustomJdbcIOWrite.DataSourceConfiguration.Builder setConnectionProperties(@Nullable String connectionProperties) {
      this.connectionProperties = connectionProperties;
      return this;
    }
    @Override
    CustomJdbcIOWrite.DataSourceConfiguration.Builder setDataSource(@Nullable DataSource dataSource) {
      this.dataSource = dataSource;
      return this;
    }
    @Override
    CustomJdbcIOWrite.DataSourceConfiguration build() {
      return new AutoValue_JdbcIO_DataSourceConfiguration(
          this.driverClassName,
          this.url,
          this.username,
          this.password,
          this.connectionProperties,
          this.dataSource);
    }
  }

}
