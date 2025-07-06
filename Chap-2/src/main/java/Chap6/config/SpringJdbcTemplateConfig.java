package Chap6.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import Chap6.dao.SingerDao;
import Chap6.exceptions.MariaDBErrorCodesTranslator;
import Chap6.template.JdbcTemplateSingerDao;

import javax.sql.DataSource;



@Import(BasicDataSourceConfig.class)
@Configuration
public class SpringJdbcTemplateConfig {

    @Autowired
    // @Qualifier("basicDataSource") //in test we are actually passing the configs, so there is one datasource, that's why there is no error. Also the import
    DataSource dataSource;

    @Bean
    public SingerDao singerDao(){
        JdbcTemplateSingerDao singerDao = new JdbcTemplateSingerDao();
        singerDao.setJdbcTemplate(jdbcTemplate());
        return singerDao;
    }

    @Bean 
    public JdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        MariaDBErrorCodesTranslator errorTranslator = new MariaDBErrorCodesTranslator();
        jdbcTemplate.setExceptionTranslator(errorTranslator);
        return jdbcTemplate;
    }

}
