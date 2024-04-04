package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Damage;
import ru.geekbrains.hexcore.utils.Hex;

public interface Damageable {
    void getDamage(Damage damage);

    Hex getDamageableHex();

    void destroy();
}
