package ru.geekbrains.hexcore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List of Hex delta vectors with additional methods and validators.
 */
public class Path {
    List<Hex> hexList;

    public List<Hex> getHexList() {
        return hexList;
    }

    public Path() {
        this.hexList = new ArrayList<>();
    }

    public Path(List<Hex> hexList) {
        if (!isValidDeltaList(hexList))
            throw new IllegalArgumentException("Provided path is invalid. Only deltas can be used");
        this.hexList = hexList;
    }

    /**
     * Method to add hex delta
     * @param hexDelta single step delta in hex coordinates
     */
    public void addStep(Hex hexDelta) {
        if (HexDeltas.isValidDelta(hexDelta)){
            this.hexList.add(hexDelta);
        } else {
            throw new IllegalArgumentException("Invalid hex delta");
        }
    }

    /**
     * Reverse the deltas list (hexList)
     */
    public void revert() {
        Collections.reverse(hexList);
    }

    private static boolean isValidDeltaList(List<Hex> hexList) {
        return HexDeltas.isValidDeltaList(hexList);
    }
}
