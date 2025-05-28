package entities.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;
import entities.Monsters.Monster;

public class TeamDAO {
    public List<Monster> findAll() throws Exception {
        String sql = "SELECT monster1_id, monster2_id, monster3_id FROM team;";
        String monsterSql = "SELECT name, health, attack FROM monster WHERE id=?;";
        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement ps = c.prepareStatement(monsterSql);
            Statement stmt = c.createStatement()) {
                List<Monster> monsters = new ArrayList<>();
                ResultSet rs = stmt.executeQuery(sql);
                int id1 = -1;
                int id2 = -1;
                int id3 = -1;
                if (rs.next()) {
                    id1 = rs.getInt("monster1_id");
                    id2 = rs.getInt("monster2_id");
                    id3 = rs.getInt("monster3_id");
                }

                List<Integer> ids = List.of(id1, id2, id3);

                for (int id : ids) {
                    ps.setInt(1, id);
                    ResultSet monsterRs = ps.executeQuery();
                    if (monsterRs.next()) {
                        monsters.add(new Monster(monsterRs.getString("name"), monsterRs.getInt("health"), monsterRs.getInt("attack")));
                    }
                }
                return monsters;

            } catch (Exception e) {
                System.out.println(e.getMessage());
                return new ArrayList<>();
            }
    }
    
    public void updateTeam(List<Monster> monsters) throws Exception {
        String updateSql = "UPDATE team SET monster1_id=?, monster2_id=?, monster3_id=?;";
        String insertSql = "INSERT INTO team (monster1_id, monster2_id, monster3_id) VALUES (?, ? ,?);";
        String selectSql = "SELECT id FROM monster WHERE name=? AND health=? AND attack=?;";

        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement updatePs = c.prepareStatement(updateSql);
            PreparedStatement insertPs = c.prepareStatement(insertSql);
            PreparedStatement selectPs = c.prepareStatement(selectSql)) {
                Integer[] monsterIds = new Integer[3];

                for (int i = 0; i < 3; i++) {
                    if (i < monsters.size()) {
                        Monster m = monsters.get(i);
                        selectPs.setString(1, m.getName());
                        selectPs.setInt(2, m.getHealth());
                        selectPs.setInt(3, m.getAttack());
                        ResultSet rs = selectPs.executeQuery();
                        if (rs.next()) {
                            monsterIds[i] = rs.getInt("id");
                        } else {
                            monsterIds[i] = null;
                        }
                    }
                }

                for (int i = 0; i < 3; i++) {
                    if (monsterIds[i] != null) {
                        updatePs.setInt(i + 1, monsterIds[i]); 
                    } else updatePs.setNull(i + 1, java.sql.Types.INTEGER);
                }

                int rowsUpdated = updatePs.executeUpdate();

                if (rowsUpdated == 0) {
                    for (int i = 0; i < 3; i++) {
                        if (monsterIds[i] != null) insertPs.setInt(i + 1, monsterIds[i]);
                        else insertPs.setNull(i + 1, java.sql.Types.INTEGER);
                    }
                    insertPs.executeUpdate();
                }
            }
        }
}
