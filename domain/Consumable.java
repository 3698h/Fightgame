package Fightgame.domain;

import java.util.ArrayList;

public class Consumable {
    public String name;
    public int num;


    public Consumable() {
    }

    public Consumable(String name, int num) {
        this.name = name;
        this.num = num;
    }

    @Override
    public String toString() {
        return name+"(恢复"+num+"点生命值)";
    }
}
