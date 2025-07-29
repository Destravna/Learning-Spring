package Chap7.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Import(BasicDataSourceConfigHikari.class)
@Configuration
@ComponentScan(basePackages = "Chap7.dao")
@EnableTransactionManagement
public class HibernateConfig {
    private static final Logger logger = LoggerFactory.getLogger(HibernateConfig.class);
    @Autowired
    private DataSource dataSource;
    
    @Bean
    public Properties hibernateProperties() {
        Properties hibernateProperties = new Properties();
        // hibernateProperties.put(Environment.DIALECT, "org.hibernate.dialect.MariaDB106Dialect ");
        hibernateProperties.put(Environment.HBM2DDL_AUTO, "none"); //controls autimatic schema generation, non means hibernate won't touch the DB schema, use in production to avoid surprises. 
        hibernateProperties.put(Environment.FORMAT_SQL, false); //if true hibernate formats generated sql with indentation and line breaks
        hibernateProperties.put(Environment.USE_SQL_COMMENTS, false); //add commnets to generated SQL to indicate which entity or operation triggered it. 
        hibernateProperties.put(Environment.SHOW_SQL, true); // print generated sql on console or not 
        hibernateProperties.put(Environment.MAX_FETCH_DEPTH, 3); // how deep hibernate goes when fetching nested associations
        hibernateProperties.put(Environment.STATEMENT_BATCH_SIZE, 10); // enables batch inserts/updates improving performance
        hibernateProperties.put(Environment.STATEMENT_FETCH_SIZE, 50); // how many rows to fetch at a time from ResultSet
        hibernateProperties.put(Environment.JTA_PLATFORM, "org.springframework.orm.hibernate5.ConfigurableJtaPlatform");
        return hibernateProperties;
    }

    @Bean
    public LocalSessionFactoryBean sessionFactory(){
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setPackagesToScan("Chap7.entities");
        sessionFactory.setHibernateProperties(hibernateProperties());
        return sessionFactory;
    }

    @Bean public PlatformTransactionManager transactionManager() {
        var transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}
