package ru.geekbrains.hexcore.model.interfaces;

import ru.geekbrains.hexcore.model.Damage;
import ru.geekbrains.hexcore.utils.Hex;

public interface Attacking {
    Damage attack(Damageable target);

    int getDistanceTo(Hex target);
}
