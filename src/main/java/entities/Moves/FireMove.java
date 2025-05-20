package entities.Moves;

import entities.Type;

public class FireMove extends Move {
    private final double burnChance = 0.3;

    public FireMove(String nume, Type type, int damage) {
        super(nume, type, damage);
    }

    public double getBurnChance() {
        return burnChance;
    }
}
