package entities.Monsters;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.DatabaseManager;
import entities.Type;
import entities.Moves.FireMove;
import entities.Moves.GrassMove;
import entities.Moves.Move;
import entities.Moves.WaterMove;

public class MonsterDAO {
    public void insert(Monster monster, List<Move> moves) throws Exception {
        String sqlMonster = "INSERT INTO monster (name, health, attack) VALUES (?, ?, ?) RETURNING id;";
        String sqlMove = "SELECT id FROM move WHERE name=?;";
        String sqlMonsterMove = "INSERT INTO monster_move (monster_id, move_id) VALUES (?, ?);";
        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement psMonster = c.prepareStatement(sqlMonster);
            PreparedStatement psMove = c.prepareStatement(sqlMove);
            PreparedStatement psMonsterMove = c.prepareStatement(sqlMonsterMove)) {
                psMonster.setString(1, monster.getName());
                psMonster.setInt(2, monster.getHealth());
                psMonster.setInt(3, monster.getAttack());
                
                int monsterId = -1;
                ResultSet rsMonster = psMonster.executeQuery();
                if (rsMonster.next()) {
                    monsterId = rsMonster.getInt(1);
                }

                for (Move move : moves) {
                    psMove.setString(1, move.getName());
                    ResultSet rsMove = psMove.executeQuery();
                    if (rsMove.next()) {
                        int moveId = rsMove.getInt(1);
                        psMonsterMove.setInt(1, monsterId);
                        psMonsterMove.setInt(2, moveId);
                        psMonsterMove.executeUpdate();
                    }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }

    public List<Monster> findAll() throws Exception {
        String sql = "SELECT mo.name AS monster_name, mo.attack, mo.health, mv.name AS move_name, mv.type, mv.damage " +
            "FROM monster mo " + 
            "JOIN monster_move mm on mo.id = mm.monster_id " +
            "JOIN move mv on mm.move_id = mv.id;";

        Map<String, Monster> monsterMap = new HashMap<>();

        try (Connection c = DatabaseManager.getConnection();
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(sql)) {
                while (rs.next()) {
                    String name = rs.getString("monster_name");
                    int attack = rs.getInt("attack");
                    int health = rs.getInt("health");

                    Monster monster = monsterMap.get(name);

                    if (monster == null) {
                        monster = new Monster(name, health, attack);
                        monster.setMoves(new ArrayList<>());
                        monsterMap.put(name, monster);
                    }

                    String moveName = rs.getString("move_name");
                    Type type = Type.valueOf(rs.getString("type"));
                    int damage = rs.getInt("damage");

                    Move move = switch (type) {
                        case FIRE -> new FireMove(moveName, type, damage);
                        case WATER -> new WaterMove(moveName, type, damage);
                        case GRASS -> new GrassMove(moveName, type, damage);
                        default -> new Move(moveName, type, damage);
                    };
                    
                    monster.getMoves().add(move);
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        return new ArrayList<>(monsterMap.values());
    }

    public void deleteMonster(Monster monster) {
        String deleteSql = "DELETE FROM monster WHERE id=?;";
        String monsterSql = "SELECT id FROM monster WHERE name=? AND health=? AND attack=?;";

        try (Connection c = DatabaseManager.getConnection();
            PreparedStatement deletePs = c.prepareStatement(deleteSql);
            PreparedStatement monsterPs = c.prepareStatement(monsterSql)) {
                monsterPs.setString(1, monster.getName());
                monsterPs.setInt(2, monster.getHealth());
                monsterPs.setInt(3, monster.getAttack());
                ResultSet rs = monsterPs.executeQuery();

                if (rs.next()) {
                    int monsterId = rs.getInt("id");
                    deletePs.setInt(1, monsterId);
                    deletePs.executeUpdate();
                } else {
                    System.out.println("Monstrul nu a fost gasit!");
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
    }
}
