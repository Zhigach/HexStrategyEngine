package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Tile;

/**
 * Interface for Basic Tiles that are Containers for all other Tiles.
 */
public interface Container {
    void setAttachedTile(Tile tile);

    void unsetAttachedTile();
}
