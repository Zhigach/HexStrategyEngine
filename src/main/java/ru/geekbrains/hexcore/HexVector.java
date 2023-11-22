package ru.geekbrains.hexcore;

/**
 * Basic cube coordinate class, contains coordinate validation
 */
public class HexVector {
    private int s;
    private int q;
    private int r;
    public HexVector(int s, int q, int r) {
        if (s+q+r != 0)
            throw new IllegalArgumentException("Incorrect hex coordinates");
        this.s = s; this.q = q; this.r = r;
    }

    public HexVector(double s, double q, double r) {
        this(Core.roundHexVector(s, q, r));
    }

    public HexVector(HexVector hexVector) {
        if (!isCorrectHex())
            throw new IllegalArgumentException("Incorrect hex coordinates");
        this.s = hexVector.getS();
        this.q = hexVector.getQ();
        this.r = hexVector.getR();
    }

    boolean isCorrectHex() { return s  + q + r == 0; }

    public HexVector add(HexVector delta) {
        HexVector result = new HexVector(s + delta.s,q + delta.q,r + delta.r);
        if (result.isCorrectHex()) { return result; }
        return null;
    }

    /**
     * Returns string with coordinates information
     * @return String
     */
    public String info() {
        return String.format("Has coordinates (%d, %d, %d)", this.s, this.q, this.r);
    }

    public int getS() {
        return s;
    }

    public int getQ() {
        return q;
    }

    public int getR() {
        return r;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HexVector)
            return (r == ((HexVector) obj).r &&
                    q == ((HexVector) obj).q &&
                    s == ((HexVector) obj).q);
        return false;
    }

    @Override
    public int hashCode() {
        return 1000*r + 100*q + s;
    }
}
