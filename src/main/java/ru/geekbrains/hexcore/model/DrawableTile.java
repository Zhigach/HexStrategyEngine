package ru.geekbrains.hexcore.model;

import java.awt.*;

public interface DrawableTile {
    void draw(Graphics2D g2, int size, Point centerPoint);
}
