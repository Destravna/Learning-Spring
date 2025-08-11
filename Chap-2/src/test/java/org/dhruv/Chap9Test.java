package org.dhruv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import Chap7.entities.Album;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import Chap9.config.ProgrammaticTransactionCnfg;
import Chap9.config.TransactionCfg;
import Chap9.exception.TitleTooLongException;
import Chap9.services.AllService;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@SqlGroup(
    @Sql(scripts = {"classpath:test-db/schema.sql", "classpath:test-db/insert.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
)
@SpringJUnitConfig(classes = { TransactionCfg.class})
public class Chap9Test {

    private static final Logger logger = LoggerFactory.getLogger(Chap9Test.class);

    @Autowired
    AllService allService;

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
    @DisplayName("should display all the singers with albums")
    void testFindAllWithAlbums() {
        logger.debug("is debug workign");
        var singers = allService.findAllWithAlbums().toList();
        assertEquals(singers.size(), 3);
        singers.forEach(s -> {
            logger.info(s.toString());
            s.getAlbums().forEach(a -> System.out.println("\t" + a));
        });
    }

    @Test
    @DisplayName("should update a singer")
    void testUpdate() {
        var singer = allService.findByIdWithAlbums(1L);
        assertNotNull(singer.get());
        var album = singer.get().getAlbums().stream().filter(a -> a.getTitle().equals("The Search For Everything"))
                .findFirst().orElse(null);
        assertNotNull(album);
        singer.get().setFirstName("Lalit");
        singer.get().removeAlbum(album);
        allService.update(singer.get());
        singer = allService.findByIdWithAlbums(1L);
        assertNotNull(singer);
        assertEquals(singer.get().getFirstName(), "Lalit");
    }

    @Test
    @DisplayName("should return the count of all singers : 3")
    void testCountAllSingers() {
        Long count = allService.countSingers();
        assertEquals(count.longValue(), 3L);
    }

    @Test
    @DisplayName("should perform  a rollback because title too long exception ")
    void testRollBackForTitleTooLongException() {
        var singer = allService.findByIdWithAlbums(1L);
        assertNotNull(singer.get());
        logger.info(singer.toString());
        singer.get().setFirstName("Eunice Kathleen");
        singer.get().setLastName("Waymon");
        var album = new Album();
        album.setTitle("""
                    Sit there and count your fingers
                    What can you do?
                    Old girl you're through
                    Sit there, count your little fingers
                    Unhappy little girl blue
                """);
        album.setReleaseDate(LocalDate.of(1959, 2, 20));
        album.setSinger(singer.get());
        var albums = Set.of(album);
        assertThrows(TitleTooLongException.class, ()->allService.saveSingerWithAlbums(singer.get(), albums), "Title too long exception not thrown");

        var firstSinger = allService.findByIdWithAlbums(1L).orElse(null);
        assertNotEquals(firstSinger.getFirstName() , "Eunice Kathleen");

    }

    // Programmatic Config
}
