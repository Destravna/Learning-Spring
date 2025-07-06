package org.dhruv;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;


import Chap6.config.springJDBCmodeling.InsertSinger;
import Chap6.config.springJDBCmodeling.InsertSingerAlbum;
import Chap6.config.springJDBCmodeling.SelectAllSingers;
import Chap6.config.springJDBCmodeling.SelectSingerByFirstName;
import Chap6.config.springJDBCmodeling.SingerRepoImpl;
import Chap6.config.springJDBCmodeling.StoredFunctionFirstNameById;
import Chap6.config.springJDBCmodeling.StoredFunctionIdByFirstAndLastName;
import Chap6.config.springJDBCmodeling.UpdateSinger;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


// THIS WORKS BUT  the recommended way is to rely on JUnit Jupiter life-cycle management to start and stop the container. 
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({ "classpath:h2/drop-schema.sql", "classpath:h2/create-schema.sql", "classpath:h2/test-data.sql" })
@SpringJUnitConfig(classes = { StoredFunctionV1Test.TestContainersConfig.class, StoredFunctionV1Test.JdbcDemoHasConfigurationThatsWhy.class })
public class StoredFunctionV1Test {

    @Autowired
    SingerRepoImpl singerRepoImpl;
    @Autowired
    DataSource datasource;

    @Test
    void findAll() {
        var singers = singerRepoImpl.findAll();
        assertEquals(singers.size(), 3);
    }

    @Test
    void findByIdTest() {
        try {
            ClassPathResource createFunctionFile = new ClassPathResource("h2/getFirstNameById.sql");

            String createFunctionSql = new String(createFunctionFile.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            System.out.println("why no print");
            Connection con = datasource.getConnection();
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT DATABASE()");
            if (rs.next()) {
                System.out.println("Connected to DB: " + rs.getString(1));

            } else {
                System.out.println("rs has no next");
            }

            Statement createFunctionStatement = con.createStatement();
            createFunctionStatement.executeQuery(createFunctionSql);

            // var resource = new ClassPathResource("h2/getFirstNameById.sql");
            // assertTrue(resource.exists());
            // ResourceDatabasePopulator populator = new ResourceDatabasePopulator(
            //         resource);

            // populator.execute(datasource);
        }

        catch (Exception e) {
            System.out.println(e);
        }

        var singer = singerRepoImpl.findFirstNameById(1L);
        assertNotNull(singer);
        assertEquals(singer.get(), "John");
    }

    @Configuration
    public static class JdbcDemoHasConfigurationThatsWhy {

        private final DataSource dataSource;

        JdbcDemoHasConfigurationThatsWhy(DataSource dataSource) {
            this.dataSource = dataSource;
        }

        @Bean
        SelectAllSingers selectAllSingers() {
            return new SelectAllSingers(dataSource);
        }

        @Bean
        SingerRepoImpl singerRepoImpl() {
            return new SingerRepoImpl();
        }

        @Bean
        SelectSingerByFirstName selectSingerByFirstName() {
            return new SelectSingerByFirstName(dataSource);
        }

        @Bean
        UpdateSinger updateSinger() {
            return new UpdateSinger(dataSource);
        }

        @Bean
        InsertSinger insertSinger() {
            return new InsertSinger(dataSource);
        }

        @Bean
        InsertSingerAlbum insertSingerWithAlbum() {
            return new InsertSingerAlbum(dataSource);
        }

        @Bean
        StoredFunctionFirstNameById storedFunctionFirstNameById() {
            return new StoredFunctionFirstNameById(dataSource);
        }

        @Bean
        StoredFunctionIdByFirstAndLastName storedFunctionIdByFirstAndLastName() {
            return new StoredFunctionIdByFirstAndLastName(dataSource);
        }

    }

    @Configuration
    public static class TestContainersConfig {
        private static final Logger logger = LoggerFactory.getLogger(TestContainersConfig.class);
        MariaDBContainer<?> mariaDB = new MariaDBContainer<>("mariadb:latest");

        @PostConstruct
        void initialize() {
            logger.info("starting docker image");
            mariaDB.start();
        }

        @PreDestroy
        void tearDown() {
            logger.info("tearing down");
            mariaDB.stop();
        }

        @Bean
        DataSource datasource() {
            try {
                BasicDataSource dataSource = new BasicDataSource();
                dataSource.setDriverClassName(mariaDB.getDriverClassName());
                logger.info("mariadb driver name {}", mariaDB.getDriverClassName());
                dataSource.setUrl(mariaDB.getJdbcUrl());
                logger.info("mariadb url {}", mariaDB.getJdbcUrl());
                dataSource.setUsername(mariaDB.getUsername());
                logger.info("mariadb username {}", mariaDB.getUsername());
                dataSource.setPassword(mariaDB.getPassword());
                logger.info("mariadb password {}", mariaDB.getPassword());
                return dataSource;
            } catch (Exception e) {
                System.out.println(e);
                logger.error("{}", e.getMessage());
                return null;
            }
        }

    }

}
