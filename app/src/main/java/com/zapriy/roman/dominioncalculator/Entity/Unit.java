package com.zapriy.roman.dominioncalculator.Entity;

public class Unit {
    private int name;
    private int cost;
    private int icon;

    public Unit(int name, int cost, int icon) {
        this.name = name;
        this.cost = cost;
        this.icon = icon;
    }

    public int getName() {
        return name;
    }

    public int getCost() {
        return cost;
    }

    public int getIcon() {
        return icon;
    }
}
