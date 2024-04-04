package ru.geekbrains.hexcore.core;

import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.tiles.Unit;

@Slf4j
public class DamageEngine {
    Battlefield battlefield;

    public DamageEngine(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    public void attack(Unit attacker, Unit target) {
        if (attacker.getHex().findDistance(target.getHex()) <= attacker.getAttack().getRange()) {
            target.getDamage(attacker.attack(target));
            if (target.getCurrentHealth() <= 0) {
                target.destroy();
                battlefield.removeTile(target);
                log.info("{} was destroyed", this);
            }
            battlefield.updateView();
        } else {
            log.error("Target {} is out of range", target);
            throw new IllegalArgumentException("Target is unreachable");
        }
    }
}
