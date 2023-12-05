package ru.geekbrains.hexcore;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    static Tile testTile;
    @BeforeEach
    public void setNewTile() {
        testTile = new Tile(0,0,0);
    }

    @org.junit.jupiter.api.Test
    void stepInEffect() {
    }

    @org.junit.jupiter.api.Test
    void getInfo() {
    }

    @org.junit.jupiter.api.Test
    void getCoordinate() {
    }

    @org.junit.jupiter.api.Test
    void setCoordinate() {
    }

    @org.junit.jupiter.api.Test
    void step() {
    }

    @org.junit.jupiter.api.Test
    void findDistanceZeroTest() {
        Tile testTile2 = new Tile(0, 0, 0);
        int range2 = testTile.findDistance(testTile2);
        assertEquals(0, range2);
    }
    @Test
    void findDistanceNonZeroTestOne() {
        Tile testTile3 = new Tile(0, 1, -1);
        int range3 = testTile.findDistance(testTile3);
        assertEquals(1, range3);
    }
    @Test
    void findDistanceNonZeroTestTwo() {
        Tile testTile4 = new Tile(4, -4, 0);
        int range4 = testTile.findDistance(testTile4);
        assertEquals(4, range4);
    }

    @org.junit.jupiter.api.Test
    void getContactingHexes() {
    }

    @org.junit.jupiter.api.Test
    void getSurroundingHexes() {
        List<HexVector> area = testTile.getHexesInRange(1);
        for (HexVector hexVector : area) {
            System.out.println(hexVector.info());
        }
        assertEquals(7, area.size()); // includes the initial hex itself
    }

    @org.junit.jupiter.api.Test
    void move() {
    }

    @org.junit.jupiter.api.Test
    void validatePath() {
    }

    @org.junit.jupiter.api.Test
    void searchPath() {
    }

    @org.junit.jupiter.api.Test
    void getLineOfSight() {
    }
}