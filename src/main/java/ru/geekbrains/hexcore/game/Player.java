package ru.geekbrains.hexcore.game;

import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.tiles.Unit;

import java.util.List;

public abstract class Player {
    private boolean hasWon;
    String name;
    List<Unit> units;
    Battlefield battlefield = Battlefield.getInstance();

    void takeTurn() {
        for (Unit unit : units) {
            
        }
    }
}
