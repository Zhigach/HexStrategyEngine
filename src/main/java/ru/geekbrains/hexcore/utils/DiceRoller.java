package ru.geekbrains.hexcore.utils;

import lombok.AllArgsConstructor;

import java.util.Random;

/**
 * Supplementary class to roll specified set of dices
 */
@AllArgsConstructor
public class DiceRoller {
    static Random random = new Random(System.currentTimeMillis());

    /**
     * Roll specified dice set and
     */
    public static int getResult(DiceSet attackDiceSet) {
        random = new Random(System.currentTimeMillis());
        return attackDiceSet.getDiceSet().stream().mapToInt(ds -> ds.n() * random.nextInt(1, ds.d() + 1)).sum();
    }
}
