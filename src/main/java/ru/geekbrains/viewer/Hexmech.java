package ru.geekbrains.viewer;

import java.awt.*;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

/* This is a companion class to NotMyPresenter.java. It handles all the mechanics related to hexagon grids. */

public class Hexmech {

    /**
     * Create screen polygon for hex (pointy top)
     * @param hexCenter hex center Point
     * @param size hexagon circle radius
     * @return polygon to be drawn on the screen
     */
    private static Polygon hex(Point hexCenter, int size) {
        Polygon polygon = new Polygon();
        for (double angle = Math.PI/6; angle <= 2*Math.PI; angle += Math.PI/3) {
            polygon.addPoint((int) (hexCenter.x + size*cos(angle)), (int) (hexCenter.y + size*sin(angle)));
        }
        return polygon;
    }

    /**
     * Hex drawing method
     * @param hexCenter hex center Point
     * @param size hexagon circle radius
     * @param g2 graphics
     */
    public static void drawHex(Point hexCenter, int size, Graphics2D g2) {
        Polygon poly = hex(hexCenter, size);
        g2.setColor(Color.white);
        g2.fillPolygon(poly);
        g2.setColor(Color.black);
        g2.drawPolygon(poly);
    }

}