package ru.geekbrains.hexcore.utils;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Math.abs;
import static java.lang.Math.round;

/**
 * Utility class for Hex mathematics
 */
@Slf4j
public class HexMath {

    /**
     * Rounds floating point coordinates to integer Hex coordinate
     *
     * @return Hex object
     */
    public static Hex roundHex(double sD, double qD, double rD) {
        int s = round((float) sD);
        int q = round((float) qD);
        int r = round((float) rD);
        double s_diff = abs(s - sD);
        double q_diff = abs(q - qD);
        double r_diff = abs(r - rD);
        if (q_diff > r_diff && q_diff > s_diff) {
            q = -r - s;
        } else if (r_diff > s_diff) {
            r = -q - s;
        } else {
            s = -q - r;
        }
        return new Hex(s, q, r);
    }

    private static double linearInterpolation(int a, int b, double t) {
        return a + (b - a) * t;
    }

    public static Hex hexLinearInterpolation(Hex a, Hex b, double t) {
        return new Hex(linearInterpolation(a.getQ(), b.getQ(), t),
                linearInterpolation(a.getR(), b.getR(), t),
                linearInterpolation(a.getS(), b.getS(), t));
    }

}

