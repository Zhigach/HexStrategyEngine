package ru.geekbrains.hexcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battlefield { // Class Holder Singleton
    // Limits
    private Map<HexVector, Tile> tiles;

    public void putTile(Tile newTile) {
        tiles.put((HexVector) newTile, newTile);
    }

    public Tile getTileByCoordinate(HexVector coordinate) {
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
