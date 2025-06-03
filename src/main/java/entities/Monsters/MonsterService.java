package entities.Monsters;

import java.util.ArrayList;
import java.util.List;

import audit.Audit;
import entities.Moves.Move;

public class MonsterService {
    private static final MonsterCRUD CRUD;

    static {
        CRUD = new MonsterCRUD();
    }

    public static void addMonster(Monster monster, List<Move> moves) {
        try {
            CRUD.insert(monster, moves); 
            Audit.writeAudit("Add Monster");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteMonster(Monster monster) {
        try {
            CRUD.deleteMonster(monster);
            Audit.writeAudit("Delete Monster");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Monster> getMonsters() {
        try {
            Audit.writeAudit("Get Monsters");
            return CRUD.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }    
}
