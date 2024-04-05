package ru.geekbrains.hexcore.tiles.terrain;

import ru.geekbrains.hexcore.model.Terrain;

import java.awt.*;


public class River extends Terrain {

    {
        FILL_COLOR = Color.CYAN;
        passable = false;
    }

    public River(int s, int q, int r) {
        super(s, q, r);
    }

}
