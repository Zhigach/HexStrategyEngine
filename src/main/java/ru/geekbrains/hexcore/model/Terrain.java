package ru.geekbrains.hexcore.model;

import ru.geekbrains.hexcore.model.interfaces.Container;

/**
 * Basic abstract Terrain that can contain attached unit
 */
public abstract class Terrain extends Tile implements Container {
    Unit attachedUnit = null;

    protected Terrain(int s, int q, int r) {
        super(s, q, r);
    }

    protected Terrain(Hex hex) {
        super(hex);
    }

    @Override
    public void setAttachedTile(Tile tile) {
        if (tile instanceof Unit) {
            this.attachedUnit = (Unit) tile;
        } else {
            throw new UnsupportedOperationException("Terrain can contain only Unit.");
        }
    }

    @Override
    public void unsetAttachedTile() {
        this.attachedUnit = null;
    }
}
