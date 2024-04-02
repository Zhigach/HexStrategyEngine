package ru.geekbrains.hexcore.model.interfaces;

@FunctionalInterface
public interface Attacking {
    void attack(Damageable target);
}
