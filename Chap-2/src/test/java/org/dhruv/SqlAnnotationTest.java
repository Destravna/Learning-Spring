package org.dhruv;

import Chap6.config.EmbeddedJdbcConfig;
import Chap6.config.springJDBCmodeling.SingerRepoImpl;
import Chap6.config.springJDBCmodeling.JdbcModelingDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.context.jdbc.SqlMergeMode;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;




import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@Sql({"classpath:h2/drop-schema.sql", "classpath:h2/create-schema.sql"})
@SpringJUnitConfig(classes = {EmbeddedJdbcConfig.class, SingerRepoImpl.class, JdbcModelingDemo.class})
public class SqlAnnotationTest {
    private static final Logger logger = LoggerFactory.getLogger(SqlAnnotationTest.class);

    @Autowired SingerRepoImpl singerRepoImpl;


    @Test
    @DisplayName("should return all singers")
    @Sql(value = "classpath:h2/test-data.sql")
    void testFindAllWithMappingSqlQuery(){
        var singers = singerRepoImpl.findAll();
        assertEquals(singers.size(), 3);
        singers.forEach(singer -> logger.info("{}", singer));
    }

    @Test
    @DisplayName("should return imagine monkeys")
    @SqlGroup({
        @Sql(statements = "insert into SINGER (first_name, last_name, birth_date) values ('imagine', 'monkeys', '1926-09-18')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD),
        @Sql(statements =  "delete from SINGER where first_name = 'imagine'")
    })
    void testFindByName(){
        var singer = singerRepoImpl.findByFirstName("imagine");
        assertNotNull(singer);
        logger.info("{}", singer);
    }




}
