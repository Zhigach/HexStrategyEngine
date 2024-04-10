package ru.geekbrains.hexcore.core.model.interfaces;

import ru.geekbrains.hexcore.core.model.tiles.Tile;

/**
 * Interface for Basic Tiles that are Containers for all other Tiles.
 */
public interface Container {
    void setAttachedTile(Tile tile);

    void unsetAttachedTile();
}
