package ru.geekbrains.hexcore.tiles;

import ru.geekbrains.hexcore.utils.Hex;

public class Plain extends Terrain {
    public Plain(Hex hex) {
        super(hex);
        passable = true;
    }
}
