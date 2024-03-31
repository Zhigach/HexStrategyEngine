package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.model.DrawableTile;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Movable;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.model.Tile;

import java.awt.*;
import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public abstract class Unit extends Tile implements Movable, DrawableTile {

    final int movementRange = 0;

    protected Unit(int s, int q, int r) {
        super(s, q, r);
    }
    protected Unit(Hex hex) {
        super(hex);
    }

    /**
     * Method to move Unit. Touched Terrains effects triggered automatically.
     * Unit attaches and detaches from Terrains while moving.
     * @param path list of hexes
     */
    public void move(Path path) {
        if (validatePath(path)) {
            for (Hex delta : path.getHexList()) {
                getBattlefield().getTerrainByCoordinate(hex).stepOutEffect(this);
                hex.add(delta);
                getBattlefield().getTerrainByCoordinate(hex).stepInEffect(this);
            }
        }
    }

    @Override
    public void draw(Graphics2D g2, int size, Point centerPoint) {
        g2.setColor(Color.BLACK);
        String shortName = getClass().getSimpleName().chars().filter(Character::isUpperCase).map(c -> ((char) c))
                .collect(StringBuilder::new,StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString();
        g2.drawString(shortName, centerPoint.x, centerPoint.y);
    }
}
