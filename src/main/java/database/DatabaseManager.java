package database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DatabaseManager {
    private static Connection connection;

    public static Connection getConnection() throws SQLException, IOException {
        if (connection == null || connection.isClosed()) {
            Properties props = new Properties();

            try (InputStream input = DatabaseManager.class.getClassLoader().getResourceAsStream("db/properties")) {
                if (input == null) {
                    throw new IOException("Resource db/properties not found");
                }
                props.load(input);
            }

            String url = props.getProperty("url");
            String user = props.getProperty("user");
            String password = props.getProperty("password");
            connection = DriverManager.getConnection(url, user, password);
        }
        return connection;
    }
}
