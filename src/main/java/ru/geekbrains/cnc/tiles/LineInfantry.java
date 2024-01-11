package ru.geekbrains.cnc.tiles;

import ru.geekbrains.hexcore.TileTypes.Unit;

public class LineInfantry extends Unit {
    public LineInfantry(int s, int q, int r) {
        super(s,q,r);
    }

    @Override
    public void stepInEffect(Unit unit) {
        //Dummy
    }

    @Override
    public void stepInEffect() {
        //Dummy
    }

    @Override
    public void stepOutEffect(Unit unit) {
        //dummy
    }

    @Override
    public void stepOutEffect() {
        //dummy
    }
    @Override
    public void stop() {

    }
}
