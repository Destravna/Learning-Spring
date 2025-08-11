package org.dhruv;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.util.Properties;

import org.hibernate.cfg.Environment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import Chap10.config.AuditCfg;
import Chap10.config.DataJpaCnfg;
import Chap10.entities.SingerAudit;
import Chap10.service.SingerAuditService;
import jakarta.annotation.PostConstruct;

@Testcontainers
@Sql({ "classpath:testcontainers/audit/drop-schema.sql", "classpath:testcontainers/audit/create-schema.sql" })
@SpringJUnitConfig(classes = { AuditServiceTest.TestContainersConfig.class })
public class AuditServiceTest {

    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");

    @Autowired
    SingerAuditService singerAuditService;

    private static final Logger logger = LoggerFactory.getLogger(AuditServiceTest.class);

    @BeforeEach
    void setUp() {
        var singer = new SingerAudit();
        singer.setFirstName("BB");
        singer.setLastName("King");
        singer.setBirthDate(LocalDate.of(1940, 8, 16));
        singerAuditService.save(singer);
    }

    @Test
    public void testFindAll() {
        assertNotNull(singerAuditService);
        SingerAudit singer = singerAuditService.findAll().findFirst().orElse(null);
        
        assertAll("testFindAll",
                () -> assertNotNull(singer),

                () -> {
                    var singerLastModifiedDate = singer.getLastModifiedDate();
                    assertNotNull(singerLastModifiedDate);
                    logger.info("lastModifiedDate : {}", singerLastModifiedDate);
                },
                () -> {
                    var createdby = singer.getCreatedBy();
                    assertNotNull(createdby);
                    logger.info("created by : {}", createdby);
                },
                () -> {
                    var creationDate = singer.getCreatedDate();
                    assertNotNull(creationDate);
                    logger.info("creation date : {}", creationDate);
                },
                () -> {
                    var version = singer.getVersion();
                    assertNotNull(version);
                    logger.info("version : {}", version);
                }
                
        );

    }

    @Configuration
    @Import({ DataJpaCnfg.class, AuditCfg.class })
    public static class TestContainersConfig {
        @Autowired
        Properties jpaProperties;

        @PostConstruct
        public void initialize() {
            jpaProperties.put(Environment.FORMAT_SQL, true);
            jpaProperties.put(Environment.USE_SQL_COMMENTS, true);
            jpaProperties.put(Environment.SHOW_SQL, true);
            jpaProperties.put(Environment.STATEMENT_BATCH_SIZE, 30);
        }

    }

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName", mariadb::getDriverClassName);
        registry.add("jdbc.url", mariadb::getJdbcUrl);
        registry.add("jdbc.username", mariadb::getUsername);
        registry.add("jdbc.password", mariadb::getPassword);
        registry.add("logging.level.root", () -> "DEBUG");
    }

}
