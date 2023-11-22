package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.HexVector;
import ru.geekbrains.hexcore.Tile;

public class Unit extends Tile {

    int size;
    String country;
    public Unit(int s, int q, int r) {
        super(s, q, r);
    }

    @Override
    public void stepInEffect() {
        return;
    }

    void move(HexVector vector){
        setCoordinate(this.add(vector));
    }
}
