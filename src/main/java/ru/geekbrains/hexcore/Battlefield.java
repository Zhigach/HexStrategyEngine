package ru.geekbrains.hexcore;

import java.util.*;

public class Battlefield { // Class Holder Singleton
    // Limits
    private HashMap<HexVector, > tiles;

    public void putTile(Tile newTile) {
        tiles.add(newTile);
    }

    public Tile getTileByCoordinate(HexVector coordinate) {
        tiles.
        return tiles.get(coordinate);
    }

    private Battlefield() {
        tiles = new HashMap<>();
    }

    private static class BattlefieldHolder {
        protected static final Battlefield HOLDER_INSTANCE = new Battlefield();
    }

    public static Battlefield getInstance() {
        return BattlefieldHolder.HOLDER_INSTANCE;
    }
}
