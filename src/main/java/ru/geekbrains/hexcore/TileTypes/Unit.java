package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.Hex;
import ru.geekbrains.hexcore.Tile;

public abstract class Unit extends Tile {
    protected Unit(int s, int q, int r) {
        super(s, q, r);
    }
    protected Unit(Hex hex) {
        super(hex);
    }

}
