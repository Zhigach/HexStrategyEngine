package ru.geekbrains.viewer.interfaces;

import ru.geekbrains.hexcore.core.model.tiles.Hex;

import java.awt.*;

/**
 * Interface to be implemented on Presenters
 */
public interface BattlefieldPresenter {
    void draw();

    void draw(Hex hex, Graphics2D g2);
}
