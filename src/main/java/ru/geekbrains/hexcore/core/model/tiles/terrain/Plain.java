package ru.geekbrains.hexcore.core.model.tiles.terrain;

import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Terrain;

/**
 * Placeholder class for empty Tiles
 */
public class Plain extends Terrain {
    public Plain(Hex hex) {
        super(hex);
        passable = true;
    }
}
