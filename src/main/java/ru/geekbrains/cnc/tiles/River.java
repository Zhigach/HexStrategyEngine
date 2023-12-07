package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.TileTypes.Terrain;


public class River extends Terrain {

    {
        passable = false;
    }
    public River(int s, int q, int r) {
        super(s, q, r);
    }
}
