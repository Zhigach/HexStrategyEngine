package ru.geekbrains.hexcore.core.service;

import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.core.model.tiles.Hex;
import ru.geekbrains.hexcore.core.model.tiles.Path;
import ru.geekbrains.hexcore.core.model.tiles.Unit;

/**
 * Supplementary Engine that manages Unit movements
 */
@Slf4j
public class MovementEngine {
    Battlefield battlefield;

    public MovementEngine(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    /**
     * Processes Unit movement. Any checks/validations to be done in this method
     *
     * @param unit moving unit
     * @param path path to be used for movement
     */

    public void move(Unit unit, Path path) {
        for (Hex hexDelta : path.getHexList()) {
            unit.move(hexDelta);
            battlefield.updateView();
            if (unit.getMovementPoints() <= 0) {
                unit.stop();
                break;
            }
        }
        log.debug("{} moved using path: {}. New coordinate is {}", unit, path, unit.getHex());
    }
}
