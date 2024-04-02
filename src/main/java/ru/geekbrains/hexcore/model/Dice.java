package ru.geekbrains.hexcore.model;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
public class Dice {
    static Random random = new Random(System.currentTimeMillis());

    public static int getResult(int tthrows, int d) {
        random = new Random(System.currentTimeMillis());
        return tthrows * random.nextInt(0, d + 1);
    }
}
