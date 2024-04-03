package ru.geekbrains.viewer.interfaces;

import ru.geekbrains.hexcore.model.Hex;

import java.awt.*;

public interface BattlefieldPresenter {
    void draw();

    void draw(Hex hex, Graphics2D g2);
}
