package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Damage;

/**
 * Interface for classes that can be damaged
 */
public interface Damageable extends Destroyable {
    void getDamage(Damage damage);
}
