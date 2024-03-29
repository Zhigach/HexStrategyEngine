package ru.geekbrains.hexcore.model;

import java.util.HashMap;
import java.util.List;

@FunctionalInterface
public interface MapInitializer {
    HashMap<Hex, List<Tile>> initializeMap(int top, int bottom, int left, int right);
}
