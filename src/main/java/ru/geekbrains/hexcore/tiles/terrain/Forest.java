package ru.geekbrains.hexcore.tiles.terrain;

import ru.geekbrains.hexcore.model.Terrain;

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
