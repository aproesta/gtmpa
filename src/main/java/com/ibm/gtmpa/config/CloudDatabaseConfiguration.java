package com.ibm.gtmpa.config;

import javax.inject.Inject;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;

import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.ibm.gtmpa.config.liquibase.AsyncSpringLiquibase;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import liquibase.integration.spring.SpringLiquibase;

@Configuration
@Profile(Constants.SPRING_PROFILE_CLOUD)
public class CloudDatabaseConfiguration extends AbstractCloudConfig {

	private final Logger log = LoggerFactory.getLogger(CloudDatabaseConfiguration.class);
	@Inject
	private Environment env;

	@Bean(destroyMethod = "close")
	public DataSource dataSource() {
		log.info("Configuring JDBC datasource from a cloud provider - AP");

		HikariConfig config = new HikariConfig();
		config.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
		config.addDataSourceProperty("url", "jdbc:postgresql://aws-us-east-1-portal.8.dblayer.com:10641/gtmpa");
		config.addDataSourceProperty("user", "admin");
		config.addDataSourceProperty("password", "NUEJOKCWRVOLUJOP");

		return new HikariDataSource(config);
	}

	@Bean
	public SpringLiquibase liquibase(DataSource dataSource, DataSourceProperties dataSourceProperties,
			LiquibaseProperties liquibaseProperties) {

		// Use liquibase.integration.spring.SpringLiquibase if you don't want
		// Liquibase to start asynchronously
		SpringLiquibase liquibase = new AsyncSpringLiquibase();
/*
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:config/liquibase/master.xml");
		liquibase.setContexts(liquibaseProperties.getContexts());
		liquibase.setDefaultSchema(liquibaseProperties.getDefaultSchema());
		liquibase.setDropFirst(liquibaseProperties.isDropFirst());

		liquibase.setShouldRun(false);
*/
		return liquibase;
	}

	@Bean
	public Hibernate4Module hibernate4Module() {
		return new Hibernate4Module();
	}
}
