package ru.geekbrains.hexcore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Battlefield { // Class Holder Singleton
    // Limits
    List<Tile> list = new ArrayList<>();
    Map<HexVector, Tile> tiles;

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
