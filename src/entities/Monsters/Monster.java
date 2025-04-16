package entities.Monsters;

import java.util.Objects;

import entities.Moves.Move;

public class Monster {
    private String name;
    private int health;
    private int attack;
    private Move[] moves = new Move[2];

    public Monster() {
        name = "Default name";
        health = 0;
        attack = 0;
    }  

    public Monster(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.attack = attack;
    }

    public void setMoves(Move[] moves) {
        int index = 0;
        for (Move move : moves) {
            moves[index] = move;
            index++;
        }
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public String toString() {
        return "Monster " + name + " attack " + attack + " health " + health + " moves " + moves.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Monster monster = (Monster) obj;
        return name == monster.name && health == monster.health && attack == monster.attack;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, health, attack);
    }
}
