package Chap6.config.springJDBCmodeling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import Chap6.pojos.Singer;


@Component
public class SelectAllSingers extends MappingSqlQuery<Singer> {

    public SelectAllSingers(DataSource dataSource){
        super(dataSource, "select * from SINGER");
    }

    @Override
    protected Singer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Singer singer = new Singer();
        singer.setId(rs.getLong("id"));
        singer.setFirstName(rs.getString("first_name"));
        singer.setLastName(rs.getString("last_name"));
        singer.setBirthDate(rs.getDate("birth_date").toLocalDate());
        singer.setAlbums(new HashSet<>());
        return singer;

    }

}
