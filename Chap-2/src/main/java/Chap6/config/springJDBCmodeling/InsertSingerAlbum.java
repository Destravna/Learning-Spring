package Chap6.config.springJDBCmodeling;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.BatchSqlUpdate;
import org.springframework.stereotype.Component;

@Component
public class InsertSingerAlbum extends BatchSqlUpdate {
    public static final int BATCH_SIZE = 10; //once we have 10 records automatically sends, otherwise we have to flush manually. 
    public static final String INSERT_SINGER_ALBUM = "insert into ALBUM (singer_id, title, release_date) " + "values (:singer_id, :title, :release_date)";

    public InsertSingerAlbum(DataSource dataSource) {
        super(dataSource, INSERT_SINGER_ALBUM);
        declareParameter(new SqlParameter("singer_id", Types.INTEGER));
        declareParameter(new SqlParameter("title", Types.VARCHAR));
        declareParameter(new SqlParameter("release_date", Types.DATE));
        setBatchSize(BATCH_SIZE);
    }
}
