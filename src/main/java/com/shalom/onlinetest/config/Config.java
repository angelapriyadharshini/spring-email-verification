package com.shalom.onlinetest.config;

import java.beans.PropertyVetoException;
import java.util.Properties;
import java.util.logging.Logger;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@EnableWebSecurity
@ComponentScan(basePackages = "com.shalom.onlinetest")
@PropertySource("classpath:persistence-mysql.properties")
public class Config {

	// properties variable
	@Autowired
	private Environment env;
	
	// logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();

		viewResolver.setPrefix("WEB-INF/view/");
		viewResolver.setSuffix(".jsp");

		return viewResolver;
	}
	
	// bean for security datasource
	
	@Bean
	public DataSource securityDS() {
		// connection pool
		ComboPooledDataSource securityDS = new ComboPooledDataSource();
		
		// jdbc driver classs
		try {
			securityDS.setDriverClass(env.getProperty("jdbc.driver"));
		} catch (PropertyVetoException ex) {
			throw new RuntimeException(ex);
		}
		// log connection properties
		logger.info(">>> jdbc.url=" +env.getProperty("jdbc.url"));
		logger.info(">>> jdbc.user=" +env.getProperty("jdbc.user"));
		
		// set  db connection properties (optional)
		securityDS.setJdbcUrl(env.getProperty("jdbc.url"));
		securityDS.setUser(env.getProperty("jdbc.user"));
		securityDS.setPassword(env.getProperty("jdbc.password"));
		
		// connection pool properties
		securityDS.setInitialPoolSize(getIntProperty("connection.pool.initialPoolSize"));
		securityDS.setMinPoolSize(getIntProperty("connection.pool.minPoolSize"));
		securityDS.setMaxPoolSize(getIntProperty("connection.pool.maxPoolSize"));
		securityDS.setMaxIdleTime(getIntProperty("connection.pool.maxIdleTime"));
		
		return securityDS;
	}
	
	
	
	private int getIntProperty(String propertyName) {
		String property = env.getProperty(propertyName);
		
		// to int
		int intproperty = Integer.parseInt(property);
		
		return intproperty;
	}
	
	private Properties getHibernateProperties() {

		// set hibernate properties
		Properties props = new Properties();

		props.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
		props.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
	
		return props;				
	}

	
	@Bean
	public LocalSessionFactoryBean sessionFactory(){
		
		// create session factorys
		LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
		
		// set the properties
		sessionFactory.setDataSource(securityDS());
		sessionFactory.setPackagesToScan(env.getProperty("hiberante.packagesToScan"));
		sessionFactory.setHibernateProperties(getHibernateProperties());
		
		return sessionFactory;
	}
	
	@Bean
	@Autowired
	public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
		
		// setup transaction manager based on session factory
		HibernateTransactionManager txManager = new HibernateTransactionManager();
		txManager.setSessionFactory(sessionFactory);

		return txManager;
	}
}
