package ru.geekbrains.hexcore.utils;

import lombok.AllArgsConstructor;

import java.util.Random;

@AllArgsConstructor
public class Dice {
    static Random random = new Random(System.currentTimeMillis());

    public static int getResult(int tthrows, int d) {
        random = new Random(System.currentTimeMillis());
        return tthrows * random.nextInt(1, d + 1);
    }
}
