package entities.Monsters;

import java.util.ArrayList;
import java.util.List;

import entities.Moves.Move;
import entities.Moves.MoveService;

public class MonsterService {
    private static final MonsterDAO DAO;

    static {
        DAO = new MonsterDAO();
        List<Move> movesList = MoveService.getMoves();
        try {
            if (movesList.size() < 2) {
                System.out.println("Nu sunt destule atacuri pentru a initializa un monstru");
            }
            else if (DAO.findAll().isEmpty()) {
                DAO.insert(new Monster("Starter 1", 100, 50), movesList);
                DAO.insert(new Monster("Starter 2", 250, 20), movesList);
                DAO.insert(new Monster("Starter 3", 80, 80), movesList);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void addMonster(Monster monster, List<Move> moves) {
        try {
            DAO.insert(monster, moves); 
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteMonster(Monster monster) {
        try {
            DAO.deleteMonster(monster);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Monster> getMonsters() {
        try {
            return DAO.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }    
}
