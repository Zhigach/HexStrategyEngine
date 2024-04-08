package ru.geekbrains.hexcore.utils;

import ru.geekbrains.hexcore.model.Hex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Class containing legal shortest hex vectors
 */
public class HexDelta {
    public static List<Hex> HEX_DELTAS = new ArrayList<>();

    static {
        HEX_DELTAS.add(new Hex(+1, 0, -1));
        HEX_DELTAS.add(new Hex(+1, -1, 0));
        HEX_DELTAS.add(new Hex(0, -1, +1));
        HEX_DELTAS.add(new Hex(-1, 0, +1));
        HEX_DELTAS.add(new Hex(-1, +1, 0));
        HEX_DELTAS.add(new Hex(0, +1, -1));
    }

    public static boolean isValidDelta(Hex hex) {
        return HEX_DELTAS.contains(hex);
    }

    public static boolean isValidDeltaList(List<Hex> hexList) {
        return new HashSet<>(HEX_DELTAS).containsAll(hexList);
    }

}
