package ru.geekbrains.hexcore.model.interfaces;

import java.awt.*;

/**
 * Supporting interface for Views
 */
@FunctionalInterface
public interface DrawableTile {
    void draw(Graphics2D g2, int size, Point centerPoint);
}
