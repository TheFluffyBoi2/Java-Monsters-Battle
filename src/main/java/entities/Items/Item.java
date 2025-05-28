package entities.Items;

public class Item {
    private String name;
    private int healingAmmount;

    public Item() {
        name = "Default name";
        healingAmmount = 0;
    }

    public Item(String name, int healingAmmount) {
        this.name = name;
        this.healingAmmount = healingAmmount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void getHealingAmmount(int healingAmmount) {
        this.healingAmmount = healingAmmount;
    }

    public String getName() {
        return name;
    }

    public int getHealingAmmount() {
        return healingAmmount;
    }
}
