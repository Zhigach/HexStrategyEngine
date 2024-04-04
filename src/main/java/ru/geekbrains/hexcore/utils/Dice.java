package ru.geekbrains.hexcore.utils;

import lombok.AllArgsConstructor;
import ru.geekbrains.hexcore.model.AttackDices;

import java.util.Random;

@AllArgsConstructor
public class Dice {
    static Random random = new Random(System.currentTimeMillis());

    public static int getResult(AttackDices attackDices) {
        random = new Random(System.currentTimeMillis());
        return attackDices.number() * random.nextInt(1, attackDices.d() + 1);
    }
}
