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

    public Path(List<Hex> hexList) {
        if (!isValidDeltaList(hexList))
            throw new IllegalArgumentException("Provided path is invalid. Only deltas can be used");
        this.hexList = hexList;
    }

    public Path() {
        this.hexList = new ArrayList<>();
    }

    public Path addStep(Hex hex) {
        this.hexList.add(hex);
        return this;
    }

    private static boolean isValidDeltaList(List<Hex> hexList) {
        return HexDeltas.isValidDeltaList(hexList);
    }
}
