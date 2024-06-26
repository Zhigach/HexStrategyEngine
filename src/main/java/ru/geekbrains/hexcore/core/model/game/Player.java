package ru.geekbrains.hexcore.core.model.game;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.geekbrains.hexcore.core.model.tiles.Unit;
import ru.geekbrains.hexcore.core.service.Battlefield;
import ru.geekbrains.hexcore.core.service.GameEngine;

import java.util.ArrayList;
import java.util.List;

/**
 * Just a basic player class. Create your own in order to describe the way to take turn
 */
@Data
@RequiredArgsConstructor
public abstract class Player {
    @NonNull
    public String name;
    protected List<Unit> units = new ArrayList<>();
    protected Battlefield battlefield = Battlefield.getInstance();
    protected GameEngine gameEngine;

    /**
     * Override this in order to state how turns are taken
     */
    public abstract void takeTurn();

    public void addUnit(Unit unit) {
        units.add(unit);
    }

    /**
     * When killed, unit must be removed from a list
     */
    public void destroyUnit(Unit unit) {
        unit.destroy();
        units.remove(unit);
    }
}
