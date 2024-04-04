package ru.geekbrains.hexcore.core;

import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.tiles.Unit;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class Player {
    private boolean hasWon = false;
    @NonNull String name;
    List<Unit> units = new ArrayList<>();
    Battlefield battlefield = Battlefield.getInstance();

    protected void takeTurn() {
        for (Unit unit : units) {
            unit.restoreMovementPoint();
        }
    }
}
