package ru.geekbrains.hexcore;

import org.junit.jupiter.api.Test;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.HexDeltas;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HexDeltasTest {
    static Hex testHex = new Hex(0,1,-1);
    static Hex testHex3 = new Hex(0,2,-2);
    static Hex testHex2 = new Hex(0,1,-1);
    static List<Hex> hexList = new ArrayList<>();

    @Test
    void isValidDeltaTrue() {
        assertTrue(HexDeltas.isValidDelta(testHex));
    }
    @Test
    void isValidDeltaFalse() {
        assertFalse(HexDeltas.isValidDelta(testHex3));
    }

    @Test
    void isValidDeltaListTrue() {
        hexList.add(testHex);
        hexList.add(testHex2);
        assertTrue(HexDeltas.isValidDeltaList(hexList));
    }

    @Test
    void isValidDeltaListFalse() {
        hexList.add(testHex);
        hexList.add(testHex2);
        hexList.add(testHex3);
        assertFalse(HexDeltas.isValidDeltaList(hexList));
    }
}