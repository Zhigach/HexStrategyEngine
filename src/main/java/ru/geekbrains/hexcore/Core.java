package ru.geekbrains.hexcore;

import static java.lang.Math.abs;
import static java.lang.Math.round;

public class Core {
    // Must contain filed limits

    public static HexVector roundHexVector(double sD, double qD, double rD) {
        int s = round((float) sD);
        int q = round((float) qD);
        int r = round((float) rD);
        double s_diff = abs(s - sD);
        double q_diff = abs(q - qD);
        double r_diff = abs(r - rD);
        if (q_diff > r_diff && q_diff > s_diff) {
            q = - r -s;
        } else if (r_diff > s_diff) {
            r = -q - s;
        } else {
            s = - q - r;
        }
        return new HexVector(s, q, r);
    }

    public static double linearInterpolation(int a, int b, double t) {
        return a + (b - a)*t;
    }
    public static HexVector hexLinearInterpolation(HexVector a, HexVector b, double t) {
        return new HexVector(linearInterpolation(a.getQ(), b.getQ(), t),
                linearInterpolation(a.getR(), b.getR(), t),
                linearInterpolation(a.getS(), b.getS(), t));
    }

}

