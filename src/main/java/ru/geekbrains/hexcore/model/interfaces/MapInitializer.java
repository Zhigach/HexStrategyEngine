package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;

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
