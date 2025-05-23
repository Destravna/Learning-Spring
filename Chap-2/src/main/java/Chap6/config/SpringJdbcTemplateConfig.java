package Chap6.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import Chap6.dao.SingerDao;
import Chap6.plain.JdbcSingerDao;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Import(BasicDataSourceConfig.class)
@Configuration
public class SpringJdbcTemplateConfig {
    private static final Logger logger = LoggerFactory.getLogger(SpringJdbcTemplateConfig.class);       

    @Autowired
    // @Qualifier("basicDataSource") //in test we are actually passing the configs, so there is one datasource, that's why there is no error. Also the import
    DataSource dataSource;

    @Bean
    public SingerDao singerDao(){
        JdbcSingerDao singerDao = new JdbcSingerDao();
        singerDao.setDataSource(dataSource);

        return singerDao;
    }

    @Bean 
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

}
