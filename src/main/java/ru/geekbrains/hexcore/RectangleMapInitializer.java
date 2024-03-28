package ru.geekbrains.hexcore;

import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.TileTypes.PlainTerrain;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.MapInitializer;
import ru.geekbrains.hexcore.model.Tile;

import java.util.HashMap;
import java.util.List;

import static java.lang.Math.floor;

@Slf4j
public class RectangleMapInitializer implements MapInitializer {
    final int top, bottom, left, right;

    public RectangleMapInitializer(int top, int bottom, int left, int right) {
        this.top = top;
        this.bottom = bottom;
        this.left = left;
        this.right = right;
    }

    /** https://www.redblobgames.com/grids/hexagons/implementation.html#map
     * @return 
     */
    @Override
    public HashMap<Hex, List<Tile>> initializeMap() {
        HashMap<Hex, List<Tile>> tiles = new HashMap<>();
        for (int r = top; r <= bottom; r++) { // pointy top
            int r_offset = (int) floor(r/2.0); // or r>>1
            for (int q = left - r_offset; q <= right - r_offset; q++) {
                Hex hex = new Hex(q, r, -q-r);
                tiles.put(hex, List.of(new PlainTerrain(hex)));
            }
        }
        return tiles;
    }
}
