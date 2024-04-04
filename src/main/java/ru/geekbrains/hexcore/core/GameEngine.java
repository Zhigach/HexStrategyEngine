package ru.geekbrains.hexcore.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.tiles.Unit;

import java.util.List;

@Data
@Slf4j
public class GameEngine {
    private final List<Player> players;
    private final Battlefield battlefield;
    private final MovementEngine movementEngine;
    private final DamageEngine damageEngine;

    public GameEngine(List<Player> players, Battlefield battlefield) {
        this.players = players;
        this.battlefield = battlefield;
        this.movementEngine = new MovementEngine(battlefield);
        this.damageEngine = new DamageEngine(battlefield);
    }

    protected boolean isFinished() {
        for (Player player : players) {
            if (player.getUnits().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public void start() {
        while (!isFinished()) {
            for (Player player : players) {
                player.takeTurn();
            }
        }
    }

    public void moveUnit(Unit unit, Path path) {
        movementEngine.move(unit, path);
    }

    public void attack(Unit attacker, Unit target) {
        damageEngine.attack(attacker, target);
    }

}
