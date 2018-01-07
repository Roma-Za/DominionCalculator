package com.zapriy.roman.dominioncalculator.Entity;

import com.zapriy.roman.dominioncalculator.Entity.Unit;
import com.zapriy.roman.dominioncalculator.R;

import java.util.ArrayList;

public class ArrUnits {
    private boolean useMeat;
    private ArrayList<Unit> units = new ArrayList<Unit>();

    public ArrUnits(boolean isUse) {
        this.useMeat = isUse;
        units.add(new Unit(R.string.spear, getCost(90, 150, 60), R.drawable.kop));
        units.add(new Unit(R.string.archer, getCost(75, 30, 15), R.drawable.luch));
        units.add(new Unit(R.string.gnome, getCost(90, 60, 42), R.drawable.gnom));
        units.add(new Unit(R.string.paladin, getCost(100, 270, 150), R.drawable.pal));
        units.add(new Unit(R.string.arbalester, getCost(140, 90, 200), R.drawable.arb));
        units.add(new Unit(R.string.rider, getCost(140, 90, 200), R.drawable.vsad));
        units.add(new Unit(R.string.patriarch, getCost(280, 180, 400), R.drawable.patr));
        units.add(new Unit(R.string.ballista, getCost(280, 180, 400), R.drawable.bal));
        units.add(new Unit(R.string.pathfinder, getCost(300, 500, 500), R.drawable.sled));
        units.add(new Unit(R.string.huntress, getCost(200, 120, 200), R.drawable.ohot));
        units.add(new Unit(R.string.virsaav, 500, R.drawable.virs));
        units.add(new Unit(R.string.outlaw, 210, R.drawable.izgoy));
        units.add(new Unit(R.string.nomad, getCost(880, 350, 530), R.drawable.koch));
        units.add(new Unit(R.string.knight, getCost(650, 1080, 436), R.drawable.ricar));
        units.add(new Unit(R.string.barbarian, getCost(950, 570, 380), R.drawable.varvar));
        units.add(new Unit(R.string.grandlord, getCost(720, 1800, 1000), R.drawable.grand));
        units.add(new Unit(R.string.horsewoman, getCost(1200, 2100, 2100), R.drawable.naezd));
        units.add(new Unit(R.string.reiter, 890, R.drawable.reitar));
        units.add(new Unit(R.string.priestess, 1880, R.drawable.jrica));
        units.add(new Unit(R.string.golem, getCost(2500, 1000, 1500), R.drawable.gol));
        units.add(new Unit(R.string.warlock, getCost(2370, 3953, 1500), R.drawable.chern));
        units.add(new Unit(R.string.daemon, getCost(2700, 1600, 1000), R.drawable.demon));
        units.add(new Unit(R.string.necromancer, getCost(2175, 5435, 3000), R.drawable.nekr));
        units.add(new Unit(R.string.renegade, 5074, R.drawable.renegat));
        units.add(new Unit(R.string.spy, getCost(450, 450, 600), R.drawable.shpion));
        units.add(new Unit(R.string.griffin, getCost(3700, 1500, 2300), R.drawable.grifon));
        units.add(new Unit(R.string.wyvern, getCost(5700, 9500, 3800), R.drawable.viv));
        units.add(new Unit(R.string.dragon, getCost(5000, 12277, 7000), R.drawable.drak));
        units.add(new Unit(R.string.chimera, getCost(4200, 7000, 7000), R.drawable.himera));
        units.add(new Unit(R.string.succubus, 1776, R.drawable.suka));
        units.add(new Unit(R.string.devourer, 3900, R.drawable.pojir));
        units.add(new Unit(R.string.assassin, 2728, R.drawable.assasin));
        units.add(new Unit(R.string.hero, 23000, R.drawable.geroy));
        units.add(new Unit(R.string.pegasus, 51800, R.drawable.pegas));
        units.add(new Unit(R.string.nate, 22500, R.drawable.neit));
        units.add(new Unit(R.string.elephant, 25500, R.drawable.elifant));
        units.add(new Unit(R.string.hydra, 75800, R.drawable.gidra));
        units.add(new Unit(R.string.gold, 1, R.drawable.gold));
        units.add(new Unit(R.string.steel, 1, R.drawable.iron));
        if (isUse) units.add(new Unit(R.string.meat, 1, R.drawable.meat));
    }

    private int getCost(int gold, int steel, int meat) {
        int cost = gold + steel;
        if (useMeat) cost += meat;
        return cost;
    }

    public ArrayList<Unit> getUnits() {
        return units;
    }
}
