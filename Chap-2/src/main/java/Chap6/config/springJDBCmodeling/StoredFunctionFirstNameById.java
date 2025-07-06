package Chap6.config.springJDBCmodeling;

import java.sql.Types;

import javax.sql.DataSource;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.SqlFunction;
import org.springframework.stereotype.Component;

@Component
public class StoredFunctionFirstNameById extends SqlFunction<String> {
    private static final String SQL_CALL = "select getfirstnamebyid(?)";

    public StoredFunctionFirstNameById (DataSource dataSource) {
        super(dataSource, SQL_CALL);
        declareParameter(new SqlParameter(Types.INTEGER));
        compile();
    }

}
