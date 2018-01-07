package com.zapriy.roman.dominioncalculator.Entity;

public class AmountUnits {
    private Unit unit;
    private int amt;

    public AmountUnits(Unit u, int amt){
        this.unit = u;
        this.amt = amt;
    }

    public Unit getUnit(){
        return unit;
    }

    public int getAmt(){
        return amt;
    }

    public int getSum(){
        return unit.getCost() * amt;
    }

}
