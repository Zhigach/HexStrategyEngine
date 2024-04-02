package ru.geekbrains.hexcore.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Attack {
    private AttackType type;
    private AttackDices attackDices;
    private int range;

    public int getDamageAmount() {
        return Dice.getResult(attackDices.number(), attackDices.d());
    }

    Attack() {
    }
}
