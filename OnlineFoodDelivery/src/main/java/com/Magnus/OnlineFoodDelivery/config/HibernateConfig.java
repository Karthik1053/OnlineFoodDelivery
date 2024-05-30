package com.Magnus.OnlineFoodDelivery.config;

import java.util.Properties;

import org.apache.log4j.*;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@EnableTransactionManagement
public class HibernateConfig 
{
	private static final Logger  LOGGER = Logger.getLogger(HibernateConfig.class);
	
    @Value("org.hibernate.dialect.SQLServerDialect")        
    private String hibernateDialect;
    @Value("true")     
    private String hibernateShowSql;
    @Value("jdbc:sqlserver://LAPTOP-BEOHV0JS;Database=EmployeeDB;trustServerCertificate=true;integratedSecurity=true;") 
    private String url;
    @Value("com.microsoft.sqlserver.jdbc.SQLServerDriver") 
    private String driverClass;

    
//Hibernate properties	    
@Bean
public Properties getHibernateProperties()
{
    Properties properties = new Properties();
    properties.put("hibernate.dialect", hibernateDialect);
    properties.put("show_sql", hibernateShowSql);
    properties.put("hibernate.connection.url", url);
    properties.put("hibernate.connection.driver_class",driverClass);
    
    return properties;
}

@Bean
public LocalSessionFactoryBean getSessionFactory()
{ 
	LOGGER.debug("Session Started!!");
	LocalSessionFactoryBean localSession = new LocalSessionFactoryBean();
	localSession.setHibernateProperties(getHibernateProperties());        
	localSession.setPackagesToScan(new String[]{"com.Magnus.OnlineFoodDelivery.bo"});
	LOGGER.debug("Session Created!!");
    return localSession;
}

@Bean
@Autowired
public HibernateTransactionManager transactionManager(SessionFactory sessionFactory)
{
    HibernateTransactionManager transaction = new HibernateTransactionManager();
    transaction.setSessionFactory(sessionFactory);
    return transaction;
}

@Bean
@Autowired
public HibernateTemplate getHibernateTemplate(SessionFactory sessionFactory)
{
    return new HibernateTemplate(sessionFactory);
}

}
