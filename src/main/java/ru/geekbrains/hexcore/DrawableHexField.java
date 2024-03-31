package ru.geekbrains.hexcore;

import ru.geekbrains.hexcore.model.Hex;

import java.awt.*;

public interface DrawableHexField {

    void draw(Graphics2D g2, Hex hex, int size, Point centerPoint);

}
