package ru.geekbrains.hexcore.TileTypes;

import ru.geekbrains.hexcore.Hex;
import ru.geekbrains.hexcore.Movable;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.Tile;

public abstract class Unit extends Tile implements Movable {

    final int movementRange = 0;

    protected Unit(int s, int q, int r) {
        super(s, q, r);
    }
    protected Unit(Hex hex) {
        super(hex);
    }

    /**
     * Method to move Unit. Touched Terrains effects triggered automatically.
     * Unit attaches and detaches from Terrains while moving.
     * @param path list of hexes
     * @return
     */
    public void move(Path path) {
        if (validatePath(path)) {
            for (Hex delta : path.getHexList()) {
                getBattlefield().getTerrainByCoordinate(hex).stepOutEffect(this);
                hex.add(delta);
                getBattlefield().getTerrainByCoordinate(hex).stepInEffect(this);
            }
        }
    }

}
