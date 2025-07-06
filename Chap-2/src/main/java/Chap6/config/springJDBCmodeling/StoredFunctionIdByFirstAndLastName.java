package Chap6.config.springJDBCmodeling;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.stereotype.Component;

// to check executing function with multiple params
@Component
public class StoredFunctionIdByFirstAndLastName extends SqlFunction<Long> {
    private static final String SQL_CALL = "select getSingerIdByName(?, ?)";    
    
    public StoredFunctionIdByFirstAndLastName(DataSource dataSource){
        super(dataSource, SQL_CALL);
        declareParameter(new SqlParameter(Types.VARCHAR));
        declareParameter(new SqlParameter(Types.VARCHAR));
        
    }
}
