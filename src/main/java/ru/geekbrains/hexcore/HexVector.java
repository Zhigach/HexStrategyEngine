package ru.geekbrains.hexcore;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Basic cube coordinate class, contains coordinate validation
 */
public class HexVector {
    private int s;
    private int q;

    public void setS(int s) {
        this.s = s;
    }

    public void setQ(int q) {
        this.q = q;
    }

    public void setR(int r) {
        this.r = r;
    }

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
        if (result.isCorrectHex())
            return result;
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

    /**
     * Returns integer range from one hex to another
     * @param to destination Tile
     * @return rounded integer range between the given hexes
     */
    public int findDistance(HexVector to) {
        return (abs(this.getQ() - to.getQ())
                + abs(this.getR() - to.getR())
                + abs(this.getS() - to.getS()))
                / 2;
    }

    /**
     * Method returns hex delta that must be added to this in order to get to the to
     * @param to destination hes
     * @return delta that must be added to this in order to get to the to
     */
    private HexVector getDelta(HexVector to) {
        return new HexVector(to.getS() - this.getS(), to.getQ() - this.getQ(), to.getR() - this.getR());
    }

    /**
     * Returns a list of contacting hexes
     * @return list of contacting hexes regardless of their properties
     */
    public List<HexVector> getContactingHexes() {
        List<HexVector> result = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            result.add(this.add(HexDeltas.HEX_DELTAS.get(i)));
        }
        return result;
    }

    /**
     * Get all hexes in a specified range. Includes the origin hex itself
     * @param range range within
     * @return List of hexes within range
     */
    public List<HexVector> getHexesInRange(int range) {
        List<HexVector> results = new ArrayList<>();
        for (int q = -range; q <= range; q++){
            int lowLimit = max(-range, -q - range);
            int upperLimit = min(range, -q + range);
            for (int r = lowLimit; r <= upperLimit; r++) {
                int s = -q - r;
                results.add(new HexVector(s, q, r));
            }
        }
        return results;
    }
}
