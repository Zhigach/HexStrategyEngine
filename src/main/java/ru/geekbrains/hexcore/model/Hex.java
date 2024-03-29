package ru.geekbrains.hexcore.model;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.utils.Core;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

/**
 * Class representing hex coordinate.
 */
@Slf4j
@Data
public class Hex {
    private int s;
    private int q;
    private int r;

    //region C_O_N_S_T_R_U_C_T_O_R_S

    public Hex(int s, int q, int r) {
        if (s + q + r != 0)
            throw new IllegalArgumentException("Incorrect hex coordinates");
        this.s = s;
        this.q = q;
        this.r = r;
    }

    public Hex(double s, double q, double r) {
        this(Core.roundHex(s, q, r));
        log.debug("Rounding called in order to get Hex");
    }

    public Hex(Hex hex) {
        this(hex.getS(), hex.getQ(), hex.getR());
    }

    //endregion

    boolean isCorrectHex() {
        return getS()  + getQ() + getR() == 0;
    }

    public Hex add(Hex delta) {
        Hex result = new Hex(getS() + delta.getS(),getQ() + delta.getQ(),getR() + delta.getR());
        if (result.isCorrectHex())
            return result;
        return null;
    }

    /**
     * Returns a list of contacting hexes
     * @return list of contacting hexes regardless of their properties
     */
    public List<Hex> getContactingHexes() {
        List<Hex> result = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            result.add(this.add(HexDeltas.HEX_DELTAS.get(i)));
        }
        log.debug(String.format("Contacting hexes requested for %s. Returning %s", this, result));
        return result;
    }

    /**
     * Get all hexes in a specified range. Includes the origin hex itself
     * @param range range within
     * @return List of hexes within range
     */
    public List<Hex> getHexesInRange(int range) {
        List<Hex> results = new ArrayList<>();
        for (int q = -range; q <= range; q++){
            int lowLimit = max(-range, -q - range);
            int upperLimit = min(range, -q + range);
            for (int r = lowLimit; r <= upperLimit; r++) {
                int s = -q - r;
                results.add(new Hex(s, q, r));
            }
        }
        return results;
    }

    /**
     * Returns integer range from one hex to another
     * @param to destination Tile
     * @return rounded integer range between the given hexes
     */
    public int findDistance(Hex to) {
        return (abs(q - to.getQ())
                + abs(r - to.getR())
                + abs(s - to.getS()))
                / 2;
    }

    /**
     * Method returns hex delta that must be added to this in order to get to the destination
     * @param destination destination hes
     * @return delta that must be added to this in order to get to the destination
     */
    public Hex getDelta(Hex destination) {
        return new Hex(destination.getS() - s, destination.getQ() - q, destination.getR() - r);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Hex)
            return (getR() == ((Hex) obj).getR() &&
                    getQ() == ((Hex) obj).getQ() &&
                    getS() == ((Hex) obj).getS());
        return false;
    }

    @Override
    public int hashCode() {
        return 1000*getR() + 100*getQ() + getS();
    }

    public String toString() {
        return String.format("<%s,%s,%s>", s, q, r);
    }
}
