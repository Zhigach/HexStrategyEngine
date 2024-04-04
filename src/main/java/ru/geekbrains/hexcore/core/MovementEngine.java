package ru.geekbrains.hexcore.core;

import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.tiles.Unit;
import ru.geekbrains.hexcore.utils.Hex;

@Slf4j
public class MovementEngine {
    Battlefield battlefield;

    public MovementEngine(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public void move(Unit unit, Path path) {
        if (unit.validatePath(path)) {
            for (Hex delta : path.getHexList()) {
                unit.getBattlefield().moveTile(unit, delta);
                unit.setHex(unit.getHex().add(delta));
                unit.reduceMovementPoints(1);
                if (unit.getMovementPoints() <= 0) {
                    unit.stop();
                    break;
                }
            }
        }
        log.debug("{} moved using path: {}. New coordinate is {}", this, path, unit.getHex());
    }
}
