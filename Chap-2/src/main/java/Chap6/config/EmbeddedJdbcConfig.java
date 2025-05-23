package Chap6.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class EmbeddedJdbcConfig {
    private static final Logger logger = LoggerFactory.getLogger(EmbeddedJdbcConfig.class);

    @Bean
    public DataSource dataSource(){
        try {
            var dbBuilder = new EmbeddedDatabaseBuilder();
            return dbBuilder.setType(EmbeddedDatabaseType.H2).addScripts("classpath:h2/schema.sql", "classpath:h2/test-data.sql").build();
        } catch (Exception e) {
            logger.error("embeded db cannot be created : ", e.getCause());
            return null;
        }
    }
}
