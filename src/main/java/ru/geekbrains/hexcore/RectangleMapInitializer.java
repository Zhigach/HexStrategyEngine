package ru.geekbrains.hexcore;

import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.model.interfaces.MapInitializer;
import ru.geekbrains.hexcore.tiles.terrain.Plain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static java.lang.Math.floor;

@Slf4j
public class RectangleMapInitializer implements MapInitializer {

    public RectangleMapInitializer() {
    }

    /**
     * @return
     */
    @Override
    public HashMap<Hex, List<Tile>> initializeMap(int top, int bottom, int left, int right) {
        HashMap<Hex, List<Tile>> tiles = new HashMap<>();
        int rowNumber = 0;
        for (int r = top; r <= bottom; r++) { // pointy top
            int r_offset = (int) floor(r / 2.0); // offset for each row
            for (int q = left - r_offset; q <= right - r_offset - rowNumber % 2; q++) { // q <= right - r_offset
                Hex hex = new Hex(-q - r, q, r);
                List<Tile> basicTileSet = new ArrayList<>();
                basicTileSet.add(new Plain(hex));
                tiles.put(hex, basicTileSet);
            }
            rowNumber++;
        }
        return tiles;
    }
}
