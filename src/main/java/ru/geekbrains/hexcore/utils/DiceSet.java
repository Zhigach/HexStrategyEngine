package ru.geekbrains.hexcore.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

/**
 * Wrapper class for Set<Dice>
 */
@AllArgsConstructor
@Setter
@Getter
public class DiceSet {
    Set<Dices> diceSet = new HashSet<>();

    public void addDices(Dices dices) {
        diceSet.add(dices);
    }
}
