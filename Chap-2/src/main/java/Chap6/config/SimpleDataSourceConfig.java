package Chap6.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

import java.sql.Driver;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class SimpleDataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(SimpleDataSourceConfig.class);

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        try {
            var dataSource = new SimpleDriverDataSource();
            logger.info(driverClassName);
            logger.info(url);
            Class<? extends Driver> driver = (Class<? extends Driver>) Class.forName(driverClassName);

            dataSource.setDriverClass(driver);
            dataSource.setUrl(url);
            dataSource.setUsername(username);
            dataSource.setPassword(password);
            return dataSource;
        } catch (Exception e) {
            logger.error("DBCP datasource bean cannot be created!", e);
            return null;
        }

    }

}
