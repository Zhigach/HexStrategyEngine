package ru.geekbrains.hexcore;

import java.util.ArrayList;
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
     * @param hexDelta single step delta in hexcoordinates
     * @return modified Path
     */
    public Path addStep(Hex hexDelta) {
        if (HexDeltas.isValidDelta(hexDelta)){
            this.hexList.add(hexDelta);
            return this;
        }
        throw new IllegalArgumentException("Invalid hex delta");
    }

    private static boolean isValidDeltaList(List<Hex> hexList) {
        return HexDeltas.isValidDeltaList(hexList);
    }
}
