package ru.geekbrains.hexcore.utils;

import lombok.AllArgsConstructor;

import java.util.Random;
import java.util.stream.IntStream;

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
        return attackDiceSet.getDiceSet().stream()
                .mapToInt(dices ->
                        IntStream
                                .range(0, dices.n())
                                .map(d -> random.nextInt(1, dices.d() + 1))
                                .sum())
                .sum();
    }
}
