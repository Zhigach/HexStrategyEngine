package ru.geekbrains.hexcore;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.TileTypes.Plain;
import ru.geekbrains.hexcore.TileTypes.Terrain;
import ru.geekbrains.hexcore.TileTypes.Unit;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.model.interfaces.MapInitializer;

import java.awt.*;
import java.util.List;
import java.util.*;

import static java.lang.Math.*;

/**
 * Class Holder Singleton containing tiles Map
 */
@Getter
@Slf4j
public class Battlefield implements DrawableHexField {
    static int top;
    static int bottom;
    static int left;
    static int right;

    private Map<Hex, List<Tile>> tiles = new HashMap<>();
    @Setter
    static MapInitializer mapInitializer;

    public int getHorizontalSize() {
        return abs(left - right);
    }

    public int getVerticalSize() {
        return abs(bottom - top);
    }

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

    public Terrain getTerrainByCoordinate(Hex hex) {
        Tile terrain = tiles.get(hex).get(0);
        if (terrain instanceof Terrain) {
            return (Terrain) terrain;
        } else {
            log.error(String.format("Default terrain was not set at %s.", hex));
            return new Plain(hex);
        }
    }

    /**
     * Method returns true if tile is passable AND not forcing entering unit to stop
     *
     * @param hex coordinate of interest
     * @return bool
     */
    public boolean isPassable(Hex hex, boolean passableOnly) {
        return getTerrainByCoordinate(hex).isPassable(passableOnly);
    }

    public boolean isPassable(Hex hex) {
        return isPassable(hex, false);
    }

    public List<Tile> getUnitsByCoordinate(Hex hex) {
        return tiles.get(hex).subList(0, tiles.get(hex).size());
    }

    public static void setDimensions(int top, int bottom, int left, int right) {
        Battlefield.top = top;
        Battlefield.bottom = bottom;
        Battlefield.left = left;
        Battlefield.right = right;
    }

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
    }

    /**
     * @param g2
     * @param hex
     * @param size
     * @param centerPoint
     */
    @Override
    public void draw(Graphics2D g2, Hex hex, int size, Point centerPoint) {
        Polygon polygon = new Polygon();
        for (double angle = Math.PI / 6; angle <= 2 * Math.PI; angle += Math.PI / 3) {
            polygon.addPoint((int) (centerPoint.x + size * cos(angle)), (int) (centerPoint.y + size * sin(angle)));
        }
        getTerrainByCoordinate(hex).draw(g2, size, centerPoint);
        for (Tile tile : getUnitsByCoordinate(hex)) {
            tile.draw(g2, size / 3, new Point(centerPoint.x - size / 2, centerPoint.y + size / 4));
        }
        g2.setColor(Color.BLACK);
        g2.drawPolygon(polygon);

        g2.drawString(hex.toString(), centerPoint.x - size / 2, centerPoint.y - size / 3);
    }


    private static class BattlefieldHolder {
        protected static final Battlefield HOLDER_INSTANCE = new Battlefield();
    }

    private Battlefield() {
    }

    public static Battlefield getInstance() {
        return BattlefieldHolder.HOLDER_INSTANCE;
    }


}
