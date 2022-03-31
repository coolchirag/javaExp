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
		return generateDataSource("newuser", "password1", false);
	}
	
	//@LiquibaseDataSource
	//@Bean
	public DataSource getLiquibasedDatasource() {
		return generateDataSource("newlbuser", "password", true);
	}
	
	private DataSource generateDataSource(String username, String password, boolean isLbDataSource) {
		DataSourceBuilder dsBuilder = DataSourceBuilder.create();
		dsBuilder.driverClassName("com.mysql.jdbc.Driver");
		dsBuilder.url("jdbc:mysql://localhost/test_db?useSSL=false");
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
					apacheDataSource.setMinIdle(1);
				}
		} else {
			final String errorMsg = "Connection pool property is not set for DataSource of type : "+dataSource.getClass(); 
			LOG.warn(errorMsg);
			throw new RuntimeException(errorMsg);
		}
		return dataSource;
	}
}
