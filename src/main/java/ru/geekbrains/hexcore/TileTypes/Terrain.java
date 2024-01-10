package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.Hex;
import ru.geekbrains.hexcore.Tile;
public class Terrain  extends Tile {
    Unit unit;

    protected Terrain(int s, int q, int r) {
        super(s, q, r);
    }

    protected Terrain(Hex hex) {
        super(hex);
    }

    public Terrain() {
        super(0,0,0);
    }

    public void setAttachedUnit(Unit unit) {
        this.unit = unit;
    }
    public void unsetAttachedUnit() {
        this.unit = null;
    }

    @Override
    public void stepInEffect(Unit unit) {
        setAttachedUnit(unit);
    }
    public void stepInEffect() {
        System.out.println(info());
    }

    @Override
    public void stepOutEffect(Unit unit) {
        unsetAttachedUnit();
    }

    @Override
    public void stepOutEffect() {

    }

}
