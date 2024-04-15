package ru.geekbrains.hexcore.utils.interfaces;

import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Tile;

import java.util.HashMap;
import java.util.List;

//TODO refactor. It's not a general case

/**
 * Interface for various Initializers
 */
@FunctionalInterface
public interface MapInitializer {
    HashMap<Hex, List<Tile>> initializeMap(int top, int bottom, int left, int right);
}
