package Chap7.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
@PropertySource("classpath:db/jdbc.properties")
public class BasicDataSourceConfigHikari {
    private static final Logger logger = LoggerFactory.getLogger(BasicDataSourceConfigHikari.class);

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Bean(destroyMethod = "close")
    public DataSource dataSource(){
        try {
            var hc = new HikariConfig();
            hc.setJdbcUrl(url);
            hc.setUsername(username);
            hc.setPassword(password);
            hc.setDriverClassName(driverClassName);
            var datasource = new HikariDataSource(hc);
            datasource.setMaximumPoolSize(10);
            return datasource;
        }
        catch (Exception e){
            logger.error("Error in creating datasource", e.getMessage());
            return null;
        }
    }
}
