package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.TileTypes.Terrain;

import java.awt.*;

public class Forest extends Terrain {
    {
        FILL_COLOR = Color.GREEN;
        passable = true;
    }
    public Forest(int s, int q, int r) {
        super(s, q, r);
    }
}
