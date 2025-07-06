package org.dhruv;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.testcontainers.containers.MariaDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import Chap6.config.BasicDataSourceConfig;
import Chap6.config.springJDBCmodeling.SingerRepoImpl;

@Testcontainers
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({ "classpath:h2/drop-schema.sql", "classpath:h2/create-schema.sql", "classpath:h2/test-data.sql" })
@SpringJUnitConfig({ BasicDataSourceConfig.class, StoredFunctionV1Test.JdbcDemoHasConfigurationThatsWhy.class })
public class StoredFunctionV2Test {

    // static @Container → container starts once for the test class.
    // @Container (non-static) → container starts fresh for every test method.
    @Container
    static MariaDBContainer<?> mariadb = new MariaDBContainer<>("mariadb:latest");

    @Autowired SingerRepoImpl singerRepoImpl;
    @Autowired BasicDataSource basicDataSource;

    @DynamicPropertySource
    static void setup(DynamicPropertyRegistry registry) {
        registry.add("jdbc.driverClassName", mariadb::getDriverClassName);
        registry.add("jdbc.url", mariadb::getJdbcUrl);
        registry.add("jdbc.username", mariadb::getUsername);
        registry.add("jdbc.password", mariadb::getPassword);
    }

    @Test
    public void findAll(){
        var res = singerRepoImpl.findAll();
        assertEquals(res.size(), 3);
        assertNotNull(basicDataSource);
    }


}
