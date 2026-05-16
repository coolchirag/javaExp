package com.example.springjpa.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

@Configuration
public class DatabaseConfig {
	
	//private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);

	@Value("${app.datasource.write.url}")
	private String writeUrl;

	@Value("${app.datasource.write.username}")
	private String writeUsername;

	@Value("${app.datasource.write.password}")
	private String writePassword;

	@Value("${app.datasource.read.url}")
	private String readUrl;

	@Value("${app.datasource.read.username}")
	private String readUsername;

	@Value("${app.datasource.read.password}")
	private String readPassword;
	
	//@Bean("testq")
	public PlatformTransactionManager transactionManager(
			ObjectProvider<TransactionManagerCustomizers> transactionManagerCustomizers) {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManagerCustomizers.ifAvailable((customizers) -> customizers.customize(transactionManager));
		return transactionManager;
	}
	
	@Bean("writeDataSource")
	//@Bean
	//@Primary
	public DataSource writeDataSource() {
		return generateDataSource(writeUrl, writeUsername, writePassword, false);
	}

	@Bean("readOnlyDataSource")
	public DataSource readOnlyDataSource() {
		return generateDataSource(readUrl, readUsername, readPassword, false);
	}

	@Primary
	@Bean("dataSource")
	public DataSource dataSource(
			@Qualifier("writeDataSource") DataSource writeDataSource,
			@Qualifier("readOnlyDataSource") DataSource readOnlyDataSource) {
		Map<Object, Object> targetDataSources = new HashMap<>();
		targetDataSources.put(DataSourceType.WRITE, writeDataSource);
		targetDataSources.put(DataSourceType.READ, readOnlyDataSource);

		TransactionRoutingDataSource routingDataSource = new TransactionRoutingDataSource();
		routingDataSource.setTargetDataSources(targetDataSources);
		routingDataSource.setDefaultTargetDataSource(writeDataSource);
		routingDataSource.afterPropertiesSet();
		return new LazyConnectionDataSourceProxy(routingDataSource);
		//return routingDataSource;
	}
	
	@LiquibaseDataSource
	@Bean("liquibaseDataSource")
	public DataSource liquibaseDataSource(@Qualifier("writeDataSource") DataSource writeDataSource) {
		return writeDataSource;
	}
	
	private DataSource generateDataSource(String url, String username, String password, boolean isLbDataSource) {

		/*
		 * String importCert = " -import "+ " -alias mysqlServerCACert "+ " -file " +
		 * "/home/chiragjivani/temp/DigiCertGlobalRootCA.crt.pem" +
		 * " -keystore truststore "+ " -trustcacerts " +
		 * " -storepass password -noprompt "; String genKey = " -genkey -keyalg rsa " +
		 * " -alias mysqlClientCertificate -keystore keystore " +
		 * " -storepass password123 -keypass password " + " -dname CN=MS "; try {
		 * sun.security.tools.keytool.Main.main(importCert.trim().split("\\s+")); }
		 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
		 * } try { sun.security.tools.keytool.Main.main(genKey.trim().split("\\s+")); }
		 * catch (Exception e) { // TODO Auto-generated catch block e.printStackTrace();
		 * }
		 * 
		 * // use the generated keystore and truststore
		 * 
		 * System.setProperty("javax.net.ssl.keyStore",
		 * "/home/chiragjivani/git/javaExp/springjpa/keystore");
		 * System.setProperty("javax.net.ssl.keyStorePassword","password");
		 * System.setProperty("javax.net.ssl.trustStore",
		 * "/home/chiragjivani/git/javaExp/springjpa/truststore");
		 * System.setProperty("javax.net.ssl.trustStorePassword","password");
		 */
		DataSourceBuilder<?> dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		//dsBuilder.url("jdbc:mysql://int-coding-platform-document-pipeline.mysql.database.usgovcloudapi.net/test_db?useSSL=true&serverTimezone=UTC");
		dsBuilder.url(url);
		dsBuilder.username(username);
		dsBuilder.password(password);
		DataSource dataSource = dsBuilder.build();
		if(dataSource instanceof org.apache.tomcat.jdbc.pool.DataSource) {
				org.apache.tomcat.jdbc.pool.DataSource apacheDataSource = (org.apache.tomcat.jdbc.pool.DataSource) dataSource;
				apacheDataSource.setTestWhileIdle(true);
				apacheDataSource.setValidationQuery("SELECT 1");
				apacheDataSource.setTimeBetweenEvictionRunsMillis(30000);
				if(isLbDataSource) {
					apacheDataSource.setMinIdle(1);
					apacheDataSource.setMaxIdle(3);
				} else {
					apacheDataSource.setMinIdle(3);
				}
				apacheDataSource.setMaxActive(10);
				apacheDataSource.setInitialSize(apacheDataSource.getMinIdle());
		} else {
			final String errorMsg = "Connection pool property is not set for DataSource of type : "
					+ (dataSource == null ? "null" : dataSource.getClass());
			//LOG.warn(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		return dataSource;
	}
}
