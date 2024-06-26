package ru.geekbrains.hexcore.core.model.interfaces;

import ru.geekbrains.hexcore.core.model.attack.Damage;

/**
 * Interface for classes that can be damaged
 */
public interface Damageable {
    void getDamage(Damage damage);
}
