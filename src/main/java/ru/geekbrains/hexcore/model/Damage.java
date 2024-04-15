package ru.geekbrains.hexcore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data transfer object, lol
 */
@AllArgsConstructor
@Setter
@Getter
public class Damage {
    private AttackType attackType;
    private int damage;

    @Override
    public String toString() {
        return String.format("%s %s", damage, attackType);
    }
}
