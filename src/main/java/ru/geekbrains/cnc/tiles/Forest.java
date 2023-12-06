package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.TileTypes.Terrain;

public class Forest extends Terrain {
    {
        passable = true;
    }

    public Forest(int s, int q, int r) {
        super(s, q, r);
    }
}
