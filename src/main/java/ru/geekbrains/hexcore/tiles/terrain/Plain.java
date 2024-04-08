package ru.geekbrains.hexcore.tiles.terrain;

import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Terrain;

/**
 * Placeholder class for empty Tiles
 */
public class Plain extends Terrain {
    public Plain(Hex hex) {
        super(hex);
        passable = true;
    }
}
