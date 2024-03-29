package ru.geekbrains.hexcore;

import org.junit.jupiter.api.BeforeEach;
import ru.geekbrains.hexcore.TileTypes.Unit;

import static org.junit.jupiter.api.Assertions.*;

class TileTest {
    static Tile testTile;
    @BeforeEach
    public void setNewTile() {
        testTile = new Tile(new Hex(0, 0, 0)) {
            @Override
            public void stepInEffect(Unit unit) {
            }

            @Override
            public void stepInEffect() {
            }

            @Override
            public void stepOutEffect(Unit unit) {
            }

            @Override
            public void stepOutEffect() {
            }
        };
    }

    @org.junit.jupiter.api.Test
    void setCoordinate() {
        Hex hex = new Hex(1,0,-1);
        testTile.setHex(hex);
        assertEquals(hex, testTile.getHex());
        testTile.setHex(new Hex(0,0,0));
    }

    @org.junit.jupiter.api.Test
    void step() {

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