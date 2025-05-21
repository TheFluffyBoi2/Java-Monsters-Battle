package entities.Player;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import database.DatabaseManager;
import entities.Monsters.Monster;
import entities.Monsters.MonsterService;

public class Player {
    private static int index = 0;
    
    public static int getIndex() {
        return index;
    }
    
    static {
        try {
            List<Monster> monsters = findAll();
            if (monsters.isEmpty()) {
                monsters = MonsterService.getMonsters();
                updateTeam(monsters);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Monster> findAll() throws Exception {
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
    
    public static void updateTeam(List<Monster> monsters) throws Exception {
        String monsterSql = "";
        if (!findAll().isEmpty()) {
            monsterSql = "UPDATE team SET monster1_id=?, monster2_id=?, monster3_id=?;";
        } else {
            monsterSql = "INSERT INTO team (monster1_id, monster2_id, monster3_id) VALUES (?, ? ,?);";
        }
        String selectSql = "SELECT id FROM monster WHERE name=? AND health=? AND attack=?;";

        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement ps = c.prepareStatement(monsterSql);
            PreparedStatement selectPs = c.prepareStatement(selectSql)) {
                int cont = 1;
                for (Monster monster : monsters) {
                    selectPs.setString(1, monster.getName());
                    selectPs.setInt(2, monster.getHealth());
                    selectPs.setInt(3, monster.getAttack());
                    ResultSet rs = selectPs.executeQuery();
                    if (rs.next()) {
                        ps.setInt(cont, rs.getInt("id"));
                    }
                    cont++;
                }
            ps.executeUpdate();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}
