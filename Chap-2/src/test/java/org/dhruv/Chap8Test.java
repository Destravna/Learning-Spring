package org.dhruv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
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
import Chap7.entities.Singer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap8.config.JpaConfig;
import Chap8.service.SingerService;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({ "classpath:test-db/schema.sql", "classpath:test-db/insert.sql" })
@SpringJUnitConfig(classes = { JpaConfig.class })
public class Chap8Test {

    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");
    @Autowired
    private SingerService singerServiceImpl;
    private static final Logger logger = LoggerFactory.getLogger(Chap8Test.class);

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName", mariadb::getDriverClassName);
        registry.add("jdbc.url", mariadb::getJdbcUrl);
        registry.add("jdbc.username", mariadb::getUsername);
        registry.add("jdbc.password", mariadb::getPassword);
    }

    @Test
    @DisplayName("find by criteria query")
    public void testFindByCriteriaQuery(){
        var singers = singerServiceImpl.findByCriteriaQuery("john", "");
        singers.forEach(s->logger.info("{}", s));
    }
    @Test
    @DisplayName("find all test")
    public void testFindAll() {
        var singers = singerServiceImpl.findAll().toList();
        assertEquals(singers.size(), 3);
        singers.forEach(s -> logger.info("{}", s));
    }

    @Test
    @DisplayName("should insert a singer with associations")
    public void testInsert() {
        Singer singer = HibernateDemo.getSingerWithAlbum();
        singerServiceImpl.save(singer);
        testFindAll();
    }

    @Test
    @DisplayName("should update a singer")
    void testUpdate() {
        var singer = singerServiceImpl.findById(2L).orElse(null);
        assertNotNull(singer);
        assertEquals(singer.getFirstName(), "Ben");
        var album = singer.getAlbums().stream().filter(a -> a.getTitle().equals("This world is")).findFirst().orElse(null);
        assertNotNull(album);
        singer.setFirstName("Eunice");
        singer.setLastName("Waymon");
        singer.removeAlbum(album);
        int version = singer.getVersion();
        singerServiceImpl.save(singer);
        singer = singerServiceImpl.findById(2L).orElse(null);
        assertNotEquals(version, singer.getVersion());
        assertNotEquals(singer.getFirstName(), "Ben");
        assertEquals(singer.getFirstName(), "Eunice");
    }

    @Test
    @DisplayName("should delted a singer")
    public void testDelete() {
        testFindAll();
        var singer = singerServiceImpl.findById(1L).orElse(null);
        assertNotNull(singer);
        singerServiceImpl.delete(singer);
        var deleted = singerServiceImpl.findById(1L);
        assertTrue(deleted.isEmpty());
    }
}
