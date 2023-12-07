package ru.geekbrains.hexcore;

import ru.geekbrains.hexcore.TileTypes.Terrain;
import ru.geekbrains.hexcore.TileTypes.Unit;

import java.util.*;

/**
 * Class Holder Singleton containing tiles Map
 */
public class Battlefield {
    // TODO: Add limits
    private final Map<Hex, List<Tile>> tiles;

    public void putTile(Hex hex, Tile newTile) {
        if (tiles.containsKey(hex)) {
            List<Tile> content = tiles.get(hex);
            Tile firstElement = content.get(0);
            boolean hasUnit = false;
            for (Tile tile : content) {
                if (tile instanceof Unit) {
                    hasUnit = true;
                    break;
                }
            }
            if (firstElement instanceof Terrain && newTile instanceof Terrain) {
                throw new IllegalArgumentException("Terrain can't be added to existing Terrain Hex");
            } else if (hasUnit && newTile instanceof Unit) {
                throw new IllegalArgumentException("Several units can't be placed at single Hex");
            } else if (firstElement instanceof Terrain && newTile instanceof Unit) {
                if (!firstElement.isPassable()){
                    throw new IllegalArgumentException("Unit can't be placed at impassable Terrain");
                } else {
                    content.add(newTile);
                }
            } else if (firstElement instanceof Unit && newTile instanceof Terrain) {
                content.add(newTile);
                Collections.swap(content, 0, content.size() - 1);
            } else {
                content.add(newTile);
            }
        } else {
            tiles.put(hex, new ArrayList<>());
            tiles.get(hex).add(newTile);
        }
    }

    public Tile getTerrainByCoordinate(Hex hex) {
        return tiles.get(hex).get(0);
    }
    public boolean isPassable(Hex hex) {
        return getTerrainByCoordinate(hex).isPassable();
    }
    public List<Tile> getTileByCoordinate(Hex hex) {
        return tiles.get(hex);
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
