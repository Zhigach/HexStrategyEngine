package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.HexVector;
import ru.geekbrains.hexcore.Tile;

import java.util.List;

public class Terrain extends Tile {
    boolean passable;

    List<Unit> mustStopAfterEntered; // list of units that must stop after entering the tile

    protected Terrain(int s, int q, int r, boolean passable) {
        super(s, q, r);
        this.passable = passable;
    }

    public Terrain(HexVector hexVector, boolean passable) {
        this(hexVector.getS(), hexVector.getQ(), hexVector.getR(), passable);
    }



    @Override
    public void stepInEffect() {
        System.out.println(getInfo());
    }
}
