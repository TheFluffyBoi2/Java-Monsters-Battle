package entities.Moves;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;
import entities.Type;

public class MoveDAO {
    public void insert(Move move) throws Exception {
        String sql = "INSERT INTO move (name, type, damage, effect, effect_chance) VALUES (?, ?, ?, ?, ?);";
        try (Connection c = DatabaseManager.getConnection(); 
            PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, move.getName());
            ps.setString(2, move.getType().name());
            ps.setInt(3, move.getDamage());

            if (move instanceof FireMove) {
                ps.setString(4, "BURN");
                ps.setDouble(5, ((FireMove) move).getBurnChance());
            } else if (move instanceof GrassMove) {
                ps.setString(4, "LEECH");
                ps.setDouble(5, ((GrassMove) move).getLeechChange());
            } else if (move instanceof WaterMove) {
                ps.setString(4, "FLINCH");
                ps.setDouble(5, ((WaterMove) move).getFlinchChance());
            } else {
                ps.setNull(4, Types.VARCHAR);
                ps.setNull(5, Types.DOUBLE);
            }
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public List<Move> findAll() throws Exception {
        String sql = "SELECT * FROM move;";
        List<Move> list = new ArrayList<>();
        try (Connection c = DatabaseManager.getConnection();
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(sql)) {
                while (rs.next()) {
                    switch (rs.getString("type")) {
                        case "FIRE":
                            list.add(new FireMove(rs.getString("name"), Type.FIRE, rs.getInt("damage")));
                            break;
                        case "WATER":
                            list.add(new WaterMove(rs.getString("name"), Type.WATER, rs.getInt("damage")));
                            break;
                        case "GRASS":
                            list.add(new GrassMove(rs.getString("name"), Type.GRASS, rs.getInt("damage")));
                            break;
                        case "NORMAL":
                            list.add(new Move(rs.getString("name"), Type.NORMAL, rs.getInt("damage")));
                            break;
                        default:
                            break;
                    }
                }
            return list;
        }
    }
}
