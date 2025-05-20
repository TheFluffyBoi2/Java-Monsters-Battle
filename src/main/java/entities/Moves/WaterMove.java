package entities.Moves;

import entities.Type;

public class WaterMove extends Move {
    private final double flichChance = 0.25;

    public WaterMove(String nume, Type type, int damage) {
        super(nume, type, damage);
    }

    public double getFlinchChance() {
        return flichChance;
    }
}
