package com.example.springjpa.config;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class DatabaseConfig {
	
	private static final Logger LOG = LoggerFactory.getLogger(DatabaseConfig.class);
	
	@Bean
	@Primary
	public DataSource getDatasource() {
		//return generateDataSource("test_db@int-coding-platform-document-pipeline", "password", false);
		return generateDataSource("newuser", "password2", false);
	}
	
	@LiquibaseDataSource
	@Bean
	public DataSource getLiquibasedDatasource() {
		//return generateDataSource("test_db@int-coding-platform-document-pipeline", "password", true);
		return generateDataSource("newuser", "password2", true);
	}
	
	private DataSource generateDataSource(String username, String password, boolean isLbDataSource) {

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
		DataSourceBuilder dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName("com.mysql.cj.jdbc.Driver");
		//dsBuilder.url("jdbc:mysql://int-coding-platform-document-pipeline.mysql.database.usgovcloudapi.net/test_db?useSSL=true&serverTimezone=UTC");
		dsBuilder.url("jdbc:mysql://localhost:3306/test_db");
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
				apacheDataSource.setInitialSize(apacheDataSource.getMinIdle());
		} else {
			final String errorMsg = "Connection pool property is not set for DataSource of type : "+dataSource.getClass(); 
			LOG.warn(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		return dataSource;
	}
}
