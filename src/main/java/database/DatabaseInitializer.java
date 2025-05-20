package database;

import java.sql.Statement;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseInitializer {
    public static void initialize() throws SQLException, IOException {
        try (Connection c = DatabaseManager.getConnection();
            InputStream schemaStream = DatabaseManager.class.getClassLoader().getResourceAsStream("db/schema.sql");
            Statement stmt = c.createStatement()) {
            if (schemaStream == null) {
                throw new IOException("Nu a fost gasit fisierul schema.sql");
            }
            String sql = new String(schemaStream.readAllBytes());
            stmt.execute(sql);
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
    } 
}