package ru.geekbrains.hexcore;

import java.util.ArrayList;
import java.util.List;

public class Path {
    List<Hex> hexList;

    public Path(List<Hex> hexList) {
        if (!isValidDeltaList(hexList))
            throw new IllegalArgumentException("Provided path is invalid. Only deltas can be used");
        this.hexList = hexList;
    }

    public Path() {
        this.hexList = new ArrayList<>();
    }

    public Path addDelta(Hex hex) {
        this.hexList.add(hex);
        return this;
    }
    private static boolean isValidDeltaList(List<Hex> hexList) {
        for (Hex hex : hexList) {
            for (Hex valid : HexDeltas.HEX_DELTAS) {
                if (hex.equals(valid))
                    return true;
            }
        }
        return false;
    }
}
