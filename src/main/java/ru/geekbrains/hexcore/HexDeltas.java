package ru.geekbrains.hexcore;

import java.util.ArrayList;
import java.util.List;

/**
 * Class containing legal shortest hex vectors
 */
public class HexDeltas {
    public static List<HexVector> HEX_DELTAS = new ArrayList<>();

    static {
        HEX_DELTAS.add(new HexVector(+1, 0, -1));
        HEX_DELTAS.add(new HexVector(+1, -1, 0));
        HEX_DELTAS.add(new HexVector(0, -1, +1));
        HEX_DELTAS.add(new HexVector(-1, 0, +1));
        HEX_DELTAS.add(new HexVector(-1, +1, 0));
        HEX_DELTAS.add(new HexVector(0, +1, -1));
    }
}
