package ru.geekbrains.hexcore.core.model.interfaces;

import ru.geekbrains.hexcore.core.model.tiles.Hex;

/**
 * Basic interface for Units or whatever can move
 */
public interface Movable {
    /**
     * Move by specified hex delta
     *
     * @param delta only HexDelta allowed here
     */
    void move(Hex delta);

    void stop();

    void reduceMovementPoints(int value);

    void restoreMovementPoint();
}
