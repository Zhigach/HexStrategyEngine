package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Hex;

/**
 * Basic interface for Units or whatever can move
 */
public interface Movable {
    void move(Hex delta);

    void stop();
}
