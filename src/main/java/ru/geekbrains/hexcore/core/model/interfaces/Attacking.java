package ru.geekbrains.hexcore.core.model.interfaces;

import ru.geekbrains.hexcore.core.model.attack.Damage;

/**
 * Interface for species that can attack others
 */
@FunctionalInterface
public interface Attacking {
    /**
     * Basic attack method. Implementing classes provide the outgoing Damage here
     *
     * @return Damage object with type and value
     */
    Damage attack(Damageable target);

}
