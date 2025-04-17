package entities.Moves;

import entities.Type;

public class MoveFactory {
    public static Move createMove(String name, Type type, int damage) {
        switch (type) {
            case FIRE: 
                return new FireMove(name, type, damage);
            case WATER:
                return new WaterMove(name, type, damage);
            case GRASS:
                return new GrassMove(name, type, damage);
            case NORMAL:
                return new Move(name, type, damage);
            default:
                return null;
        }
    }
}
