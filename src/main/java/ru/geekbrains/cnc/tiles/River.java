package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.HexVector;
import ru.geekbrains.hexcore.Tile;

public class River extends Tile {

    public River(int s, int q, int r) {
        super(s, q, r);
        passable = false;
    }

    public River(HexVector hexVector) {
        super(hexVector);
        passable = false;
    }
}
