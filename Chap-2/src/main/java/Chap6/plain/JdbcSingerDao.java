package Chap6.plain;

import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap6.dao.SingerDao;
import Chap6.exceptions.MariaDBErrorCodesTranslator;

public class JdbcSingerDao implements SingerDao, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(JdbcSingerDao.class);
    private DataSource dataSource; 
    private JdbcTemplate jdbcTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (dataSource == null) {
            throw new BeanCreationException("Must set datasource in SingerDao");
        }
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
        var jdbcTemplate = new JdbcTemplate();
        var errorTranslator = new MariaDBErrorCodesTranslator();
        jdbcTemplate.setExceptionTranslator(errorTranslator);
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<String> findNameById(Long id) {
        String sql = "SELECT first_name, last_name FROM SINGER WHERE id = ?";

        try (
            var connection = dataSource.getConnection();
            var stmt = connection.prepareStatement(sql);) {
            stmt.setLong(1, id);
            try (var resultSet = stmt.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("first_name") + " " + resultSet.getString("last_name");
                    return Optional.of(name);
                }
            }
        } 

        catch (Exception ex) {
            logger.error("Error querying singer by id: {}", ex.getMessage(), ex);
        }

        return Optional.empty();

    }
}
