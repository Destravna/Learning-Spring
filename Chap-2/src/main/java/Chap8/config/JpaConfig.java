package Chap8.config;
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cfg.Environment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import Chap7.config.BasicDataSourceConfigHikari;


@Import(BasicDataSourceConfigHikari.class)
@EnableTransactionManagement
@ComponentScan(basePackages = { "Chap7.entities", "Chap8.service" })
@Configuration
public class JpaConfig {
    private static final Logger logger = LoggerFactory.getLogger(JpaConfig.class);
    @Autowired DataSource dataSource;

    @Bean
    public JpaVendorAdapter jpaVendorAdapter(){
        return new HibernateJpaVendorAdapter();
    }

    @Bean
    public Properties jpaProperties(){
        Properties jpaProps = new Properties();
        jpaProps.put(Environment.HBM2DDL_AUTO, "none");
        jpaProps.put(Environment.FORMAT_SQL, true);
        jpaProps.put(Environment.USE_SQL_COMMENTS, false);
        jpaProps.put(Environment.SHOW_SQL, true);
        return jpaProps;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(){
        var factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(dataSource);
        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setPackagesToScan("Chap7.entities");
        factory.setJpaProperties(jpaProperties());
        factory.setEntityManagerFactoryInterface(jakarta.persistence.EntityManagerFactory.class);
        
        return factory;
    }
    
    @Bean
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }


}
