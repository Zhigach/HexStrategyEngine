package ru.geekbrains.hexcore;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Terrain;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.model.Unit;
import ru.geekbrains.hexcore.model.interfaces.MapInitializer;
import ru.geekbrains.hexcore.model.interfaces.Movable;
import ru.geekbrains.hexcore.tiles.terrain.Plain;
import ru.geekbrains.viewer.interfaces.BattlefieldPresenter;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.abs;

/**
 * Singleton paradigm class containing tiles Map and their processing
 */
@Setter
@Getter
@Slf4j
public class Battlefield {
    private BattlefieldPresenter battlefieldPresenter;
    private Map<Hex, List<Tile>> tiles = new HashMap<>(); //TODO make map of <Hex, Container>. Refactor logic
    @Setter
    static MapInitializer mapInitializer;
    public static int top;
    public static int bottom;
    public static int left;
    public static int right;
    private boolean isInitialized = false;

    public int getHorizontalSize() {
        return abs(left - right);
    }

    public int getVerticalSize() {
        return abs(bottom - top);
    }

    /**
     * Base logic method to put Tile on a specific coordinate (hex)
     *
     * @param hex     target coordinate
     * @param newTile Tile to put
     */
    public void putTile(Hex hex, Tile newTile) {
        if (hex == null) {
            log.error("Error occurred.", new IllegalArgumentException("Coordinate can't be null"));
            throw new IllegalArgumentException("Coordinate can't be null");
        }
        if (tiles.containsKey(hex)) {
            log.trace(String.format("Battlefield already contains %s hex.", hex));
            List<Tile> content = tiles.get(hex);
            Tile firstElement = content.get(0);
            boolean hasUnit = false;
            hasUnit = content.stream().anyMatch(tile -> tile instanceof Unit);

            if (firstElement instanceof Terrain && newTile instanceof Terrain) {
                if (firstElement instanceof Plain) {
                    log.info(String.format("Replacing placeholder Plain Terrain at %s with %s", hex, newTile));
                    content.set(0, newTile);
                } else {
                    log.error(String.format("%s is already taken by Terrain (%s).", hex, firstElement));
                    throw new IllegalArgumentException("Terrain can't be added to existing Terrain Hex");
                }
            } else if (hasUnit && newTile instanceof Unit) {
                log.error(String.format("%s is already taken by Unit.", hex));
                throw new IllegalArgumentException("Several units can't be placed at single Hex");
            } else if (firstElement instanceof Terrain && newTile instanceof Unit) {
                if (!firstElement.isPassable()) {
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

    /**
     * Move Tile by HexDelta
     *
     * @param movableTile Movable Tile to be moved
     * @param delta       HexDelta to be moved by
     * @param <T>         Movable implementing movable interface
     */
    public <T extends Tile & Movable> void moveTile(T movableTile, Hex delta) {
        Hex hex = movableTile.getHex();
        getTerrainByCoordinate(hex).unsetAttachedTile();
        getTerrainByCoordinate(hex.add(delta)).setAttachedTile(movableTile);
        getTiles().get(hex).remove(movableTile);
        putTile(hex.add(delta), movableTile);
        log.info("Movable tile {} moved from {} to {}", movableTile, hex, hex.add(delta));
        updateView();
    }

    /**
     * Remove specified tile from battlefield. In case of Unit linkage with Player removed by unit itself
     */
    public void removeTile(Tile tile) {
        if (tile instanceof Terrain) {
            log.error("Terrain can't be removed in basic realization");
            throw new IllegalArgumentException("Terrain can't be removed in basic realization");
        } else {
            tiles.get(tile.getHex()).remove(tile);
        }
    }

    /**
     * Check if Hex coordinate is present on Battlefield. Method is used to check whether we are within the battlefield
     *
     * @param hex coordinate
     */
    private boolean isContainsHex(Hex hex) {
        return tiles.containsKey(hex);
    }

    /**
     * Get Tiles (any, i.e. Units, Terrain and their inheritors) located at specified Hex coordinate
     *
     * @param hex coordinate
     * @return List of tiles or empty List in case of request outside the Battlefield
     */
    private List<Tile> getTilesByCoordinate(Hex hex) {
        if (isContainsHex(hex)) {
            return tiles.get(hex);
        } else {
            return Collections.emptyList();
        }
    }

    /**
     * Get Terrain at specified Hex coordinate.
     *
     * @param hex coordinate
     * @return returns null in case of request for coordinate that outside Battlefield limits
     */
    public Terrain getTerrainByCoordinate(Hex hex) {
        List<Tile> tiles1 = getTilesByCoordinate(hex);
        if (tiles1.isEmpty()) {
            return null;
        } else {
            Tile terrain = tiles1.get(0); //Current pu logic is that is Terrain is always first
            if (terrain instanceof Terrain) {
                return (Terrain) terrain;
            } else {
                log.error(String.format("Default terrain was not set at %s.", hex));
                return new Plain(hex);
            }
        }
    }

    /**
     * Get Units at specified Hex coordinate.
     *
     * @param hex coordinate
     */
    public List<Unit> getUnitsByCoordinate(Hex hex) {
        return getTilesByCoordinate(hex)
                .stream().filter(t -> (t instanceof Unit))
                .map(t -> ((Unit) t))
                .collect(Collectors.toList());
    }

    /**
     * Method returns true if tile is passable AND not forcing entering unit to stop
     *
     * @param hex coordinate of interest
     * @return bool
     */
    public boolean isPassable(Hex hex, boolean inSingleTurn) {
        if (isContainsHex(hex)) {
            return getTilesByCoordinate(hex).stream().allMatch(t -> t.isPassable(inSingleTurn));
        }
        return false;
    }

    public boolean isPassable(Hex hex) {
        return isPassable(hex, false);
    }

    /**
     * Set map size. Only rectangular maps are supported
     *
     * @param top    top row number
     * @param bottom bottom row number
     * @param left   left column number
     * @param right  right column number
     */
    public static void setDimensions(int top, int bottom, int left, int right) {
        Battlefield.top = top;
        Battlefield.bottom = bottom;
        Battlefield.left = left;
        Battlefield.right = right;
    }

    /**
     * Proxy call MapInitializer, that is intended to add Hex coordinates to the map and place Plain Terrain
     */
    public void initializeMap() {
        final Map<Hex, List<Tile>> tiles;
        if (mapInitializer == null) {
            log.error("Map initializer must be set before battlefield is used.");
            throw new RuntimeException("Battlefield is not set up correctly.");
        } else {
            tiles = mapInitializer.initializeMap(top, bottom, left, right);
        }
        this.tiles = tiles;
        log.info("Map initialization completed successfully.");
        updateView();
        isInitialized = true;
    }


    /**
     * Supplementary internal class for Singleton template
     */
    private static class BattlefieldHolder {
        protected static final Battlefield HOLDER_INSTANCE = new Battlefield();
    }

    private Battlefield() {
    }

    /**
     * Get Singleton battlefield
     */
    public static Battlefield getInstance() {
        return BattlefieldHolder.HOLDER_INSTANCE;
    }

    /**
     * Call for View update in BattlefieldPresenter
     */
    public void updateView() {
        battlefieldPresenter.draw();
    }

}
