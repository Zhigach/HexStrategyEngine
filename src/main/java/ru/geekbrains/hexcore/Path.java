package ru.geekbrains.hexcore;

import java.util.ArrayList;
import java.util.List;

public class Path {
    List<HexVector> hexList;

    public Path(List<HexVector> hexList) {
        if (!isValidateDeltaList(hexList))
            throw new IllegalArgumentException("Provided path is invalid. Only deltas can be used");
        this.hexList = hexList;
    }

    public Path() {
        this.hexList = new ArrayList<>();
    }

    public Path addDelta(HexVector hexVector) {
        this.hexList.add(hexVector);
        return this;
    }
    private static boolean isValidateDeltaList(List<HexVector> hexList) {
        for (HexVector hexVector : hexList) {
            for (HexVector valid : HexDeltas.HEX_DELTAS) {
                if (hexVector.equals(valid))
                    return true;
            }
        }
        return false;
    }
}
