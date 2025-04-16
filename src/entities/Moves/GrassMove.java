package entities.Moves;

import entities.Type;

public class GrassMove extends Move {
    private final double leechChance = 0.5;

    public GrassMove(String name, Type type, int damage) {
        super(name, type, damage);
    }

    public double getLeechChange() {
        return leechChance;
    }
}
