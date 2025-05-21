package entities.Monsters;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import entities.Moves.Move;

public class Monster {
    private String name;
    private int health;
    private int attack;
    private List<Move> moves = new ArrayList<>();

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

    public Monster(String name, int health, int attack, List<Move> moves) {
        this.name = name;
        this.health = health;
        this.attack = attack;
        this.moves = moves;
    }

    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public int getHealth() {
        return health;
    }

    public int getAttack() {
        return attack;
    }

    public String getName() {
        return name;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Monster monster = (Monster) obj;
        return Objects.equals(name, monster.name) && health == monster.health && attack == monster.attack;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, health, attack);
    }
}
