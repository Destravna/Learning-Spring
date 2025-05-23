package Chap6.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public interface CoreDao {
    default Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mariadb://localhost:3306/music?useSSL=false", "root", "1234");
    }

    default void closeConnection(Connection connection) throws SQLException {
        if(connection == null) return;
        connection.close();
    } 
}
