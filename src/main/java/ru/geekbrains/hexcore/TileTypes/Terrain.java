package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.HexVector;
import ru.geekbrains.hexcore.Tile;
public class Terrain  extends Tile{

    protected Terrain(int s, int q, int r) {
        super(s, q, r);
    }

    protected Terrain(HexVector hexVector) {
        super(hexVector);
    }

    public Terrain() {
        super(0,0,0);
    }
}
