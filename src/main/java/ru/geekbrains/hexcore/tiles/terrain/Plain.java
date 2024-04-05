package ru.geekbrains.hexcore.tiles.terrain;

import ru.geekbrains.hexcore.model.Terrain;
import ru.geekbrains.hexcore.utils.Hex;

public class Plain extends Terrain {
    public Plain(Hex hex) {
        super(hex);
        passable = true;
    }
}
