package Chap6.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import org.apache.commons.dbcp2.BasicDataSource;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class BasicDataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(BasicDataSourceConfig.class);

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    @Bean(destroyMethod = "close", name = "basicDataSource")
    public DataSource dataSource(){
        try{
            var datasource = new BasicDataSource();
            datasource.setDriverClassName(driverClassName);
            datasource.setUrl(url);
            datasource.setUsername(username);
            datasource.setPassword(password);
            return datasource;
        }
        catch(Exception ex){
            logger.error("{}", ex.getCause());
            return null;
        }
    }
}
