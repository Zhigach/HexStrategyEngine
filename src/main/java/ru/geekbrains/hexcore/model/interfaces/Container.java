package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Tile;

public interface Container {
    void setAttachedTile(Tile tile);
    void unsetAttachedTile();
}
