package entities.Items;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;

public class ItemCRUD {
    public void insert(Item item) throws Exception {
        String sql = "INSERT INTO item (name, healing_ammount) VALUES (?, ?);";
        try (Connection c = DatabaseManager.getConnection(); 
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, item.getName());
            ps.setInt(2, item.getHealingAmmount());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Item> findAll() throws Exception {
        String sql = "SELECT * FROM item;";
        List<Item> list = new ArrayList<>();
        try (Connection c = DatabaseManager.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    list.add(new Item(rs.getString("name"), rs.getInt("healing_ammount")));
                }
            return list;
        }
    }
}
