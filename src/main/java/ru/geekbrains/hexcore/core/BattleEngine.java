package ru.geekbrains.hexcore.core;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.model.Unit;

/**
 * Supplementary Engine that manages attacks and defences
 */
@Slf4j
public class BattleEngine {
    Battlefield battlefield;

    public BattleEngine(Battlefield battlefield) {
        this.battlefield = battlefield;
    }

    /**
     * Main Engine method that asks attacker to provide his attack and passes it to target.
     * Attack validation is done here
     */
    public void attack(@NotNull Unit attacker, @NotNull Unit target) {
        if (attacker.getHex().findDistance(target.getHex()) <= attacker.getAttack().getRange() &&
                attacker.hasLOS(target)) { //FIXME exception caught
            target.getDamage(attacker.attack(target));
            if (target.getCurrentHealth() <= 0) {
                target.destroy();
                target.getOwner().destroyUnit(target);
                battlefield.removeTile(target);
                log.info("{} was destroyed", this);
            }
            battlefield.updateView();
        } else {
            log.error("Target {} is out of range or Attacker doesn't have LOS", target);
            throw new IllegalArgumentException("Target is unreachable");
        }
    }
}
