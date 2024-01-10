package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.Hex;

public class PlainTerrain extends Terrain {
    public PlainTerrain(Hex hex) {
        super(hex);
        passable = true;
    }
}
