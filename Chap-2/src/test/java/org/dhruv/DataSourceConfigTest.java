package org.dhruv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;

import java.util.Optional;
import java.util.Set;

import javax.sql.DataSource;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import Chap6.config.BasicDataSourceConfig;
import Chap6.config.EmbeddedJdbcConfig;
import Chap6.config.NamedTemplateConfig;
import Chap6.config.RowMapperConfig;
import Chap6.config.SimpleDataSourceConfig;
import Chap6.config.SpringDataSourceConfig;
import Chap6.config.SpringJdbcTemplateConfig;
import Chap6.config.springJDBCmodeling.JdbcModelingDemo;
import Chap6.config.springJDBCmodeling.SingerRepoImpl;
import Chap6.dao.SingerDao;
import Chap6.plain.RowMapperDao;
import Chap6.pojos.Singer;
import Chap6.template.NamedTemplateDao;

public class DataSourceConfigTest {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfigTest.class);

    @Test
    public void testStoredFunctionGetIdByFirstAndLastName(){
        var ctx = new AnnotationConfigApplicationContext(BasicDataSourceConfig.class, JdbcModelingDemo.class);;
        DataSource dataSource = ctx.getBean(DataSource.class);
        assertNotNull(dataSource);
        logger.info("data soucre type {}", dataSource.getClass().getName());
        SingerRepoImpl singerRepoImpl = ctx.getBean(SingerRepoImpl.class);
        assertNotNull(singerRepoImpl);
        Optional<Long> id = singerRepoImpl.findIdByFirstNameAndLastName("imagine", "monkeys");
        assertEquals(id.get(), 8L);
    }


    @Test
    public void testRowExtractor() {
        var ctx = new AnnotationConfigApplicationContext(RowMapperConfig.class);
        DataSource dataSource = ctx.getBean(DataSource.class);
        assertNotNull(dataSource);
        var rowmapper = ctx.getBean(RowMapperDao.class);
        assertNotNull(rowmapper);
        Set<Singer> singers = rowmapper.findAllWithAlbum();
        assertNotEquals(singers.size(), 0);
        singers.forEach(singer -> logger.info("{}", singer));
        ctx.close();
    }

    @Test
    public void testRowMapperDao() {
        var ctx = new AnnotationConfigApplicationContext(RowMapperConfig.class);
        DataSource dataSource = ctx.getBean(DataSource.class);
        assertNotNull(dataSource);
        var rowmapper = ctx.getBean(RowMapperDao.class);
        assertNotNull(rowmapper);
        Set<Singer> res = rowmapper.findAll();
        assertNotEquals(res.size(), 0);
        res.forEach(singer -> logger.info(singer.toString()));
        ctx.close();
    }

    @Test
    public void testNamedTemplateDao() {
        var ctx = new AnnotationConfigApplicationContext();
        ctx.register(NamedTemplateConfig.class);
        ctx.refresh();
        DataSource dataSource = ctx.getBean(DataSource.class);
        assertNotNull(dataSource);
        NamedTemplateDao namedTemplateDao = ctx.getBean(NamedTemplateDao.class);
        Optional<String> fullName = namedTemplateDao.findNameById(7L);
        if (fullName.isPresent()) {
            logger.info("id : {}", fullName.get());
        }
        ctx.close();

    }

    @Test
    public void testJdbcTemplate() {
        var ctx = new AnnotationConfigApplicationContext(SpringJdbcTemplateConfig.class);
        DataSource dataSource = ctx.getBean(DataSource.class);
        assertNotNull(dataSource);
        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);
        assertNotNull(jdbcTemplate);
        SingerDao singerDao = ctx.getBean(SingerDao.class);
        assertNotNull(singerDao);
        Optional<String> res = singerDao.findNameById(1L);
        assertEquals("John Mayer", res.get());
        ctx.close();
    }

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
        ctx.close();
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
                var resulset = statement.executeQuery();) {
            while (resulset.next()) {
                int mockVal = resulset.getInt(1);
                assertEquals(1, mockVal);

            }
        } catch (Exception e) {
            logger.error("{}", e);
            fail("database connection failed" + e.getMessage());
        }
    }
}
