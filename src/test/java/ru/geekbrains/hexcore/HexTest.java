package ru.geekbrains.hexcore;

import org.junit.jupiter.api.Test;
import ru.geekbrains.hexcore.utils.Hex;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HexTest {
    static Hex testHex = new Hex(0, 0, 0);

    @Test
    void getS() {
    }

    @Test
    void getQ() {
    }

    @Test
    void getR() {
    }

    @Test
    void setS() {
    }

    @Test
    void setQ() {
    }

    @Test
    void setR() {
    }

    @Test
    void isCorrectHex() {
    }

    @Test
    void add() {
    }


    @Test
    void findDistanceZeroTest() {
        Hex testHex2 = new Hex(0, 0, 0);
        int range2 = testHex.findDistance(testHex2);
        assertEquals(0, range2);
    }

    @Test
    void findDistanceNonZeroTestOne() {
        Hex testHex3 = new Hex(0, 1, -1);
        int range3 = testHex.findDistance(testHex3);
        assertEquals(1, range3);
    }

    @Test
    void findDistanceNonZeroTestTwo() {
        Hex testHex4 = new Hex(4, -4, 0);
        int range4 = testHex.findDistance(testHex4);
        assertEquals(4, range4);
    }

    @org.junit.jupiter.api.Test
    void getContactingHexes() {
        List<Hex> area = testHex.getContactingHexes();
        for (Hex hex : area) {
            System.out.println(hex.toString());
        }
        assertEquals(6, area.size());
    }

    @org.junit.jupiter.api.Test
    void getSurroundingHexes() {
        List<Hex> area = testHex.getHexesInRange(1);
        for (Hex hex : area) {
            System.out.println(hex.toString());
        }
        assertEquals(7, area.size()); // includes the initial hex itself
    }

    @org.junit.jupiter.api.Test
    void getSurroundingHexesTwo() {
        List<Hex> area = testHex.getHexesInRange(2);
        for (Hex hex : area) {
            System.out.println(hex.toString());
        }
        assertEquals(19, area.size()); // includes the initial hex itself
    }
}