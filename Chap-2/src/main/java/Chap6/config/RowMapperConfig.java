package Chap6.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import Chap6.exceptions.MariaDBErrorCodesTranslator;
import Chap6.plain.RowMapperDao;

@Import(BasicDataSourceConfig.class)
@Configuration
public class RowMapperConfig {

    @Autowired
    private DataSource dataSource;

    NamedParameterJdbcTemplate jdbcTemplate(){
        JdbcTemplate jdbcTemplate = new JdbcTemplate();
        jdbcTemplate.setDataSource(dataSource);
        jdbcTemplate.setExceptionTranslator(new MariaDBErrorCodesTranslator());
        return new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Bean
    RowMapperDao rowMapperDao(){
        var dao = new RowMapperDao();
        dao.setNamedTemplate(jdbcTemplate());
        return dao;
    }
    
}
