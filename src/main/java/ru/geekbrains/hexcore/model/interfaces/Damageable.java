package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Damage;

public interface Damageable {
    void getDamage(Damage damage);

    void destroy();
}
