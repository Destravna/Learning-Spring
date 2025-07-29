package org.dhruv;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import Chap7.HibernateDemo;
import Chap7.config.HibernateConfig;
import Chap7.dao.SingerDao;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({ "classpath:test-db/schema.sql", "classpath:test-db/insert.sql" })
@SpringJUnitConfig(classes = {HibernateConfig.class})
public class HibernateTest {
    private static final Logger logger = LoggerFactory.getLogger(HibernateTest.class);

    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");

    @Autowired SingerDao singerDao;

    @Test
    void testUpdate(){
        var singer = singerDao.findByid(3L);
        logger.info("retrieved singer : {}", singer);
        assertNotNull(singer);
        assertEquals(singer.getFirstName(), "John");
        singer.setFirstName("Max");
        var newSinger = singerDao.save(singer);
        assertEquals(newSinger.getFirstName(), "Max");
        assertEquals(newSinger.getLastName(), "Butler");
        testFindAll();
    }

    @Test
    @DisplayName("should return all the singers")
    void testFindAll(){
        var singers = singerDao.findAll();
        assertEquals(3, singers.size());
        singers.forEach(s -> logger.info(s.toString()));
    }

    @Test
    @DisplayName("add new singer")
    void testSaveSingerWithInstrument(){
        var singer = HibernateDemo.getSingerWithAlbum();
        singer = singerDao.saveWithInstrument(singer);
        var singers = singerDao.findAll();
        assertEquals(4, singers.size());
    }

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName", mariadb::getDriverClassName);
        registry.add("jdbc.url", mariadb::getJdbcUrl);
        registry.add("jdbc.username", mariadb::getUsername);
        registry.add("jdbc.password", mariadb::getPassword);
    }

}
