package ru.geekbrains.hexcore.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.geekbrains.hexcore.utils.DiceRoller;
import ru.geekbrains.hexcore.utils.DiceSet;

/**
 * Model for attack as a Unit attribute
 */
@Data
@AllArgsConstructor
public class Attack {
    private AttackType type;
    private DiceSet attackDices;
    private int range;

    /**
     * Roll appropriate attack dices to define damage amount
     *
     * @return damage amount
     */
    public int getDamageAmount() {
        return DiceRoller.getResult(attackDices);
    }

    Attack() {
    }
}
