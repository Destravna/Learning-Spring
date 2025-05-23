package org.dhruv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.h2.store.Data;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Chap6.config.BasicDataSourceConfig;
import Chap6.config.EmbeddedJdbcConfig;
import Chap6.config.SimpleDataSourceConfig;
import Chap6.config.SpringDataSourceConfig;
import Chap6.dao.SingerDao;

public class DataSourceConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfigTest.class);

    @Test
    public void testJdbcSingerDao() throws SQLException {
        var ctx = new AnnotationConfigApplicationContext(SpringDataSourceConfig.class);
        var dataSource = ctx.getBean(DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);
        var singerDao = ctx.getBean(SingerDao.class);
        Optional<String> nameOptional = singerDao.findNameById(1L);
        assertNotNull(nameOptional.get());
        assertEquals(nameOptional.get(), "John Mayer");
    }

    @Test
    public void testSimpeDataSource() throws SQLException {
        var ctx = new AnnotationConfigApplicationContext(SimpleDataSourceConfig.class);
        var dataSource = ctx.getBean(DataSource.class);
        assertNotNull(dataSource);
        testDataSource(dataSource);
        ctx.close();
    }

    @Test
    public void testBasicDatasource() throws SQLException {
        var ctx = new AnnotationConfigApplicationContext(BasicDataSourceConfig.class);
        var dataSource = ctx.getBean(DataSource.class);
        testDataSource(dataSource);
        ctx.close();
    }

    @Test
    public void testEmbededDs() throws SQLException {
        var ctx = new AnnotationConfigApplicationContext(EmbeddedJdbcConfig.class);
        var datasource = ctx.getBean(DataSource.class);
        testDataSource(datasource);
        ctx.close();
    }


    private void testDataSource(DataSource dataSource) throws SQLException {
        try (
            var connection = dataSource.getConnection();
            var statement = connection.prepareStatement("SELECT 1");
            var resulset = statement.executeQuery();
        ){
            while(resulset.next()){
                int mockVal = resulset.getInt(1);
                assertEquals(1, mockVal);

            }
        }
        catch(Exception e){
            logger.error("{}", e);
            fail("database connection failed" + e.getMessage());
        }
    }
}
