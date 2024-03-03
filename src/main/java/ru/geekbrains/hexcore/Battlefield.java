package ru.geekbrains.hexcore;

import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.TileTypes.PlainTerrain;
import ru.geekbrains.hexcore.TileTypes.Terrain;
import ru.geekbrains.hexcore.TileTypes.Unit;

import java.util.*;

/**
 * Class Holder Singleton containing tiles Map
 */
@Slf4j
public class Battlefield {
    // TODO: Add limits
    private final Map<Hex, List<Tile>> tiles;

    public void putTile(Hex hex, Tile newTile) {
        if (hex == null) {
            log.error("Error occurred.", new IllegalArgumentException("Coordinate can't be null"));
            throw new IllegalArgumentException("Coordinate can't be null");
        }
        if (tiles.containsKey(hex)) {
            log.debug(String.format("Battlefield already contains %s hex.", hex));
            List<Tile> content = tiles.get(hex);
            Tile firstElement = content.get(0);
            boolean hasUnit = false;
            hasUnit = content.stream().anyMatch(tile -> tile instanceof Unit);
//            for (Tile tile : content) { //TODO use stream API
//                if (tile instanceof Unit) {
//                    hasUnit = true;
//                    break;
//                }
//            }
            if (firstElement instanceof Terrain && newTile instanceof Terrain) {
                log.error(String.format("%s is already taken by Terrain (%s).", hex, firstElement));
                throw new IllegalArgumentException("Terrain can't be added to existing Terrain Hex");
            } else if (hasUnit && newTile instanceof Unit) {
                log.error(String.format("%s is already taken by Unit.", hex));
                throw new IllegalArgumentException("Several units can't be placed at single Hex");
            } else if (firstElement instanceof Terrain && newTile instanceof Unit) {
                if (!firstElement.isPassable()){
                    log.error(String.format("Trying to place Unit on impassable Terrain (%s) at %s.", firstElement, hex));
                    throw new IllegalArgumentException("Unit can't be placed at impassable Terrain");
                } else {
                    content.add(newTile);
                    log.debug(String.format("New Tile (%s) is added to the battlefield at %s.", newTile, hex));
                }
            } else if (firstElement instanceof Unit && newTile instanceof Terrain) {
                content.add(newTile);
                log.debug(String.format("New Tile (%s) is added to the battlefield at %s.", newTile, hex));
                Collections.swap(content, 0, content.size() - 1);
            } else {
                content.add(newTile);
                log.debug(String.format("New Tile (%s) is added to the battlefield at %s.", newTile, hex));
            }
        } else {
            tiles.put(hex, new ArrayList<>());
            tiles.get(hex).add(newTile);
            log.debug(String.format("New Tile (%s) is added to the battlefield at empty hex %s.", newTile, hex));
        }
    }

    public Terrain getTerrainByCoordinate(Hex hex) {
        List<Tile> hexTiles = getTileByCoordinate(hex);
        if (hexTiles == null || hexTiles.get(0) instanceof Unit) {
            return new PlainTerrain(hex);
        } else {
            return (Terrain) hexTiles.get(0);
        }
    }

    /**
     * Method returns true if tile is passable AND not forcing entering unit to stop
     * @param hex coordinate of interest
     * @return bool
     */
    public boolean isPassable(Hex hex, boolean passableOnly) {
        return getTerrainByCoordinate(hex).isPassable(passableOnly);
    }
    public boolean isPassable(Hex hex) {
        return isPassable(hex, false);
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
