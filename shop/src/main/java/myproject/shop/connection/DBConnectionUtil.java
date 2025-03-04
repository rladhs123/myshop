package myproject.shop.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static myproject.shop.connection.ConnectionConst.*;

public class DBConnectionUtil {

    public static Connection getConnection() throws SQLException {
        try {
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            return connection;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
