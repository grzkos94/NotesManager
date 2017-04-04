package com.notes.manager.configuration;

import java.sql.Driver;
import java.util.Properties;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

@Configuration
public class PersistenceConfig {
	@Bean
	HibernatePersistenceProvider hibernatePersistenceProvider() {
		return new HibernatePersistenceProvider();
	}

	@Bean
	public DriverManagerDataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setUrl("jdbc:mysql://localhost:3306/Notes");
		dataSource.setUsername("root");
		dataSource.setPassword("");
		return dataSource;
	}

	@Bean
	JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	Properties jpaProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProperties.setProperty("hibernate.show_sql", "true");

		return jpaProperties;
	}

	@Bean
	LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactory.setPackagesToScan("com.notes.manager.domain");
		entityManagerFactory.setDataSource(dataSource());
		entityManagerFactory.setJpaProperties(jpaProperties());
		entityManagerFactory.setPersistenceProvider(hibernatePersistenceProvider());

		return entityManagerFactory;
	}
}
