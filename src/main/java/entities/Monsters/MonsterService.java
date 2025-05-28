package entities.Monsters;

import java.util.ArrayList;
import java.util.List;

import audit.Audit;
import entities.Moves.Move;

public class MonsterService {
    private static final MonsterDAO DAO;

    static {
        DAO = new MonsterDAO();
    }

    public static void addMonster(Monster monster, List<Move> moves) {
        try {
            DAO.insert(monster, moves); 
            Audit.writeAudit("Add Monster");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteMonster(Monster monster) {
        try {
            DAO.deleteMonster(monster);
            Audit.writeAudit("Delete Monster");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Monster> getMonsters() {
        try {
            Audit.writeAudit("Get Monsters");
            return DAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }    
}
