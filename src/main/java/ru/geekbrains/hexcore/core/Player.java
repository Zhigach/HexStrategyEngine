package ru.geekbrains.hexcore.core;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.model.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Just a basic player class. Create your own in order to describe the way to take turn
 */
@Deprecated
@Data
@RequiredArgsConstructor
public class Player {
    @NonNull
    String name;
    List<Unit> units = new ArrayList<>();
    Battlefield battlefield = Battlefield.getInstance();

    /**
     * Override this in order to state how turns are taken
     */
    protected void takeTurn() {
        for (Unit unit : units) {
            unit.restoreMovementPoint();
        }
    }

    /**
     * When killed, unit must be removed from a list
     */
    public void destroyUnit(Unit unit) {
        units.remove(unit);
    }
}
