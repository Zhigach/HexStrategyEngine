package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.HexVector;
import ru.geekbrains.hexcore.Tile;

public class Unit extends Tile {
    protected Unit(int s, int q, int r) {
        super(s, q, r);
    }

    protected Unit(HexVector hexVector) {
        super(hexVector);
    }

    public Unit() {
        super(0,0,0);
    }
}
