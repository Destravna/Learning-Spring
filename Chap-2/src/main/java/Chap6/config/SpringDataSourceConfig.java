package Chap6.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import Chap6.dao.SingerDao;
import Chap6.plain.JdbcSingerDao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Import(BasicDataSourceConfig.class)
@Configuration
public class SpringDataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(SpringDataSourceConfig.class);

    @Autowired
    @Qualifier("basicDataSource")
    DataSource dataSource;

    @Bean
    public SingerDao singerDao(){
        JdbcSingerDao jdbcSingerDao = new JdbcSingerDao();
        jdbcSingerDao.setDataSource(dataSource);
        logger.info("datasource class : {}", dataSource.getClass().getName());
        return jdbcSingerDao;
    }

}
