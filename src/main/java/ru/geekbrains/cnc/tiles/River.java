package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.TileTypes.Terrain;

import java.awt.*;


public class River extends Terrain {

    {
        FILL_COLOR = Color.CYAN;
        passable = false;
    }
    public River(int s, int q, int r) {
        super(s, q, r);
    }

    @Override
    public void draw(Graphics2D g2, int size, Point centerPoint) {
        super.draw(g2, size, centerPoint);
    }
}
