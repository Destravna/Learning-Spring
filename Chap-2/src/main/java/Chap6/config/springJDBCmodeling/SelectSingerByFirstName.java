package Chap6.config.springJDBCmodeling;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashSet;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.stereotype.Component;

import Chap6.pojos.Singer;

@Component
public class SelectSingerByFirstName extends MappingSqlQuery<Singer> {
    // private final String sqlQuery = "select id, first_name, last_name, birth_date from singer where first_name = :first_name";
    public SelectSingerByFirstName(DataSource dataSource){
        // var sqlQuery = "select id, first_name, last_name, birth_date from singer where first_name = :first_name";
        super(dataSource, "select id, first_name, last_name, birth_date from SINGER where first_name = :first_name");
        super.declareParameter(new SqlParameter("first_name", Types.VARCHAR));
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
