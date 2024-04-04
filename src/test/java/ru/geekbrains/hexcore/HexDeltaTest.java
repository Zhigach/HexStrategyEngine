package ru.geekbrains.hexcore;

import org.junit.jupiter.api.Test;
import ru.geekbrains.hexcore.utils.Hex;
import ru.geekbrains.hexcore.utils.HexDelta;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HexDeltaTest {
    static Hex testHex = new Hex(0, 1, -1);
    static Hex testHex3 = new Hex(0, 2, -2);
    static Hex testHex2 = new Hex(0, 1, -1);
    static List<Hex> hexList = new ArrayList<>();

    @Test
    void isValidDeltaTrue() {
        assertTrue(HexDelta.isValidDelta(testHex));
    }

    @Test
    void isValidDeltaFalse() {
        assertFalse(HexDelta.isValidDelta(testHex3));
    }

    @Test
    void isValidDeltaListTrue() {
        hexList.add(testHex);
        hexList.add(testHex2);
        assertTrue(HexDelta.isValidDeltaList(hexList));
    }

    @Test
    void isValidDeltaListFalse() {
        hexList.add(testHex);
        hexList.add(testHex2);
        hexList.add(testHex3);
        assertFalse(HexDelta.isValidDeltaList(hexList));
    }
}