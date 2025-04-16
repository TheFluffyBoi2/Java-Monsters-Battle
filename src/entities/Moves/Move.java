package entities.Moves;

import entities.Type;

public class Move {
    private String name;
    private Type type;
    private int damage;
    
    public Move(String name, Type type, int damage) {
        this.name = name;
        this.type = type;
        this.damage = damage;
    }

    public Type getType() {
        return type;
    }

    public int getDamage() {
        return damage;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public String toString() {
        return name;
}
}
