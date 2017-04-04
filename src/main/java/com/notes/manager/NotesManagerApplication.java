package com.notes.manager;

import java.sql.Driver;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

import javax.servlet.ServletContext;

import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.validation.Validator;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

import com.notes.manager.validator.PasswordConfirmationValidator;
import com.notes.manager.validator.UserValidator;

@SpringBootApplication
public class NotesManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotesManagerApplication.class, args);
	}

	@Bean
	public SpringSecurityDialect springSecurityDialect() {
		return new SpringSecurityDialect();
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
	Properties jpaProperties() {
		Properties jpaProperties = new Properties();
		jpaProperties.setProperty("hibernate.hbm2ddl.auto", "update");
		jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
		jpaProperties.setProperty("hibernate.show_sql", "true");

		return jpaProperties;
	}

	@Bean
	HibernatePersistenceProvider hibernatePersistenceProvider() {
		return new HibernatePersistenceProvider();
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

	@Bean
	JpaTransactionManager transactionManager() {
		JpaTransactionManager transactionManager = new JpaTransactionManager();
		transactionManager.setDataSource(dataSource());
		return transactionManager;
	}

	@Bean
	Validator passwordConfirmationValidator() {
		return new PasswordConfirmationValidator();
	}

	@Bean
	UserValidator userValidator() {
		Set<Validator> springValidators = new HashSet<>();
		springValidators.add(passwordConfirmationValidator());

		UserValidator validator = new UserValidator();
		validator.setSpringValidators(springValidators);

		return validator;
	}
}
