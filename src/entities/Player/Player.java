package entities.Player;

import entities.Monsters.Monster;

public class Player {
    private static Monster[] monsters = new Monster[3];
    private static int index = 0;

    public static Monster[] getMonsters() {
        return monsters;
    }

    public static int getIndex() {
        return index;
    }

    public static void addMonster(Monster monster) {
        if (index < monsters.length) {
            monsters[index] = monster;
            index++;
        } else {
            index = 0;
            monsters[index] = monster;
            index++;
        }
    }


}
