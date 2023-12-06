package ru.geekbrains.hexcore;

import ru.geekbrains.hexcore.TileTypes.Terrain;
import ru.geekbrains.hexcore.TileTypes.Unit;

import java.util.*;

public class Battlefield { // Class Holder Singleton
    // TODO: Add limits
    private final Map<Hex, List<Tile>> tiles;

    public void putTile(Hex hex, Tile newTile) {
        if (tiles.containsKey(newTile)) {
            List<Tile> content = tiles.get(hex);
            Tile firstElement = content.get(0);
            if (firstElement instanceof Terrain && newTile instanceof Terrain) {
                throw new IllegalArgumentException("Terrain can't be added to existing Terrain Hex");
            } else if (firstElement instanceof Terrain && newTile instanceof Unit) {
                if (!firstElement.isPassable()){
                    throw new IllegalArgumentException("Unit can't be placed at impassable Terrain");
                } else {
                    content.add(newTile);
                }
            } else if (firstElement instanceof Unit && newTile instanceof Terrain) {
                content.add(newTile);
                Collections.swap(content, 0, content.size() - 1);
            }
        } else {
            tiles.put(hex, new ArrayList<>());
            tiles.get(hex).add(newTile);
        }
    }

    public Tile getTerrainByCoordinate(HexVector hexVector) {
        return tiles.get(hexVector).get(0);
    }
    public List<Tile> getTileByCoordinate(HexVector coordinate) {
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
