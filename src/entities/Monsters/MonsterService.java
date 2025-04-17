package entities.Monsters;

import java.util.List;
import java.util.Vector;

import entities.Moves.Move;
import entities.Moves.MoveService;

public class MonsterService {
    private static List<Monster> monsters = new Vector<>();

    static {
        Move move1 = MoveService.getMoves().get(0);
        Move move2 = MoveService.getMoves().get(1);
        Move[] moves = new Move[2];
        moves[0] = move1;
        moves[1] = move2;
        monsters.add(new Monster("Starter 1", 100, 50, moves));
        monsters.add(new Monster("Starter 2", 250, 20, moves));
        monsters.add(new Monster("Starter 3", 80, 80, moves));
    }
    
    public static void addMonster(Monster monster) {
        monsters.add(monster);
    }

    public static void deleteMonster(Monster monster) {
        monsters.remove(monster);
    }

    public static List<Monster> getMonsters() {
        return monsters;
    }    
}
