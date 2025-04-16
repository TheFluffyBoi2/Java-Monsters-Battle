package entities.Monsters;

import java.util.List;
import java.util.Vector;

public class MonsterService {
    private static List<Monster> monsters = new Vector<>();
    
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
