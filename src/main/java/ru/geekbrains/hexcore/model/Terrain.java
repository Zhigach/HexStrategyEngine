package ru.geekbrains.hexcore.model;

import ru.geekbrains.hexcore.model.interfaces.Container;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/**
 * Basic abstract Terrain that can contain attached unit
 */
public abstract class Terrain extends Tile implements Container {
    Unit attachedUnit = null;

    protected Terrain(int s, int q, int r) {
        super(s, q, r);
    }

    protected Terrain(Hex hex) {
        super(hex);
    }

    /**
     * Draw the Terrain
     *
     * @param size hexagon encircling radius
     */
    @Override
    public void draw(Graphics2D g2, int size, Point centerPoint) {
        Polygon polygon = new Polygon();
        for (double angle = Math.PI / 6; angle <= 2 * Math.PI; angle += Math.PI / 3) {
            polygon.addPoint((int) (centerPoint.x + size * cos(angle)), (int) (centerPoint.y + size * sin(angle)));
        }
        g2.setColor(FILL_COLOR);
        g2.fillPolygon(polygon);
    }

    @Override
    public void setAttachedTile(Tile tile) {
        if (tile instanceof Unit) {
            this.attachedUnit = (Unit) tile;
        } else {
            throw new UnsupportedOperationException("Terrain can contain only Unit.");
        }
    }

    @Override
    public void unsetAttachedTile() {
        this.attachedUnit = null;
    }
}
