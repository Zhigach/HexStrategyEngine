package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;

import java.awt.*;

import static java.lang.Math.*;

public class Terrain  extends Tile {
    Unit unit;

    protected Terrain(int s, int q, int r) {
        super(s, q, r);
    }

    protected Terrain(Hex hex) {
        super(hex);
    }

    public Terrain() {
        super(0,0,0);
    }

    public void setAttachedUnit(Unit unit) {
        this.unit = unit;
    }
    public void unsetAttachedUnit() {
        this.unit = null;
    }

    @Override
    public void stepInEffect(Unit unit) {
        setAttachedUnit(unit);
    }
    public void stepInEffect() {
        System.out.println(toString());
    }

    @Override
    public void stepOutEffect(Unit unit) {
        unsetAttachedUnit();
    }

    @Override
    public void stepOutEffect() {

    }

    @Override
    public void draw(Graphics2D g2, int size, Point centerPoint) {
        Polygon polygon = new Polygon();
        for (double angle = Math.PI/6; angle <= 2*Math.PI; angle += Math.PI/3) {
            polygon.addPoint((int) (centerPoint.x + size*cos(angle)), (int) (centerPoint.y + size*sin(angle)));
        }
        g2.setColor(FILL_COLOR);
        g2.fillPolygon(polygon);
    }
}
