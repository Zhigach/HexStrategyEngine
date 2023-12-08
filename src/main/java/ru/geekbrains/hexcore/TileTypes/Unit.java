package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.Hex;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.Tile;

public abstract class Unit extends Tile {
    protected Unit(int s, int q, int r) {
        super(s, q, r);
    }
    protected Unit(Hex hex) {
        super(hex);
    }

    public boolean move(Path path) {
        if (validatePath(path)) {
            for (Hex delta : path.getHexList()) {
                getBattlefield().getTerrainByCoordinate(hex).stepOutEffect(this);
                hex.add(delta);
                getBattlefield().getTerrainByCoordinate(hex).stepInEffect(this);
            }
        }
        return  false;
    }

}
