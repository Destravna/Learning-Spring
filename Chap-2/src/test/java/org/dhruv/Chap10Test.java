package org.dhruv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import Chap10.config.DataJpaCnfg;
import Chap10.service.AlbumService;
import Chap10.service.SingerService;
import Chap7.entities.Singer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Testcontainers
@SqlGroup(@Sql(scripts = { "classpath:test-db/schema.sql",
        "classpath:test-db/insert.sql" }, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS))
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@SpringJUnitConfig(classes = { DataJpaCnfg.class })
public class Chap10Test {

    private static final Logger logger = LoggerFactory.getLogger(Chap10Test.class);

    @Autowired
    SingerService singerService;

    @Autowired
    AlbumService albumService;

    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName", mariadb::getDriverClassName);
        registry.add("jdbc.url", mariadb::getJdbcUrl);
        registry.add("jdbc.username", mariadb::getUsername);
        registry.add("jdbc.password", mariadb::getPassword);
        registry.add("logging.level.root", () -> "DEBUG");
    }

    @Test
    @DisplayName("should find all the singers")
    void testFindAll() {
        assertNotNull(singerService);
        List<Singer> singers = new ArrayList<>();
        singerService.findAll().peek(s -> singers.add(s)).map(s -> s.toString()).forEach(logger::info);
        assertEquals(singers.size(), 3L);
    }

    @Test
    public void testFindByFirstName() {
        var singers = singerService.findByFirstName("John").peek(s -> logger.info(s.toString())).toList();
        assertEquals(2, singers.size());
    }

    @Test
    public void testFindByFirstNameAndLastName() {
        var singers = singerService.findByFirstNameAndLastName("John", "Mayer").peek(s -> logger.info(s.toString()))
                .toList();
        assertEquals(1, singers.size());
    }

    @Test
    @DisplayName("should find the albums of a singer")
    public void testFindAlbumsBySinger() {
        var singer = singerService.findByFirstNameAndLastName("John", "Mayer").toList().get(0);
        assertEquals(singer.getId().longValue(), 1L);
        var albums = albumService.findBySinger(singer).toList();
        assertEquals(albums.size(), 2L);
    }

    @Test
    public void testFindByTitle() {
        var albums = albumService
                .findWithXInTitle("The")
                .peek(s -> logger.info(s.toString())).toList();
        assertEquals(1, albums.size());
    }

    @Test
    public void testFindWithReleaseDateGreaterThan() {
        var albums = albumService
                .findWithReleaseDateGreaterThan(LocalDate.of(2010, 1, 1))
                .peek(s -> logger.info(s.toString())).toList();
        assertEquals(2, albums.size());
    }

    @Rollback
    @Test
    @DisplayName("should update singer's name")
    void testUpdateFirstNameById() {
        var singer = singerService.updateFirstName("dhruv", 1L);
        assertAll("singer not updated", () -> assertNotNull(singer), () -> assertEquals(singer.getFirstName(), "dhruv"));
    }

    @Test
    @DisplayName("should find the full name using id")
    void testGetFullName() {
        Object[] singer = singerService.getFullNameById(1L);
        assertNotNull(singer);
        assertNotNull(singer[0]);
        Object[] res = (Object[]) singer[0];
        assertEquals(res.length, 2);
        assertEquals(res[0].toString() + " " + res[1].toString(), "John Mayer");
    }

    @Test
    @DisplayName("projection query using interfaces")
    void testFullNameUsingInt() {
        var fullName = singerService.getFullNameByIdInt(1L).orElse(null);
        assertNotNull(fullName);
        assertEquals(fullName.getFirstName() + " " + fullName.getLastName(), "John Mayer"); 
    }


}
