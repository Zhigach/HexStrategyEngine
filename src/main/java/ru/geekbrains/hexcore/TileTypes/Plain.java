package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.model.Hex;

public class Plain extends Terrain {
    public Plain(Hex hex) {
        super(hex);
        passable = true;
    }
}
