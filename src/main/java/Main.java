import ru.geekbrains.cnc.tiles.Forest;
import ru.geekbrains.cnc.tiles.LineInfantry;
import ru.geekbrains.cnc.tiles.River;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Hex;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.Tile;
import ru.geekbrains.viewer.BattlefieldDrawer;
import ru.geekbrains.viewer.BattlefieldPresenter;

import java.util.Set;

public class Main {

    public static void main(String[] args) {

        BattlefieldDrawer battlefieldDrawer = new BattlefieldDrawer(Battlefield.getInstance());

        Tile lineInfantry = new LineInfantry(0, 0, 0);
        Tile tile3 = new Forest(0, 0, 0);

        Tile river1 = new River(0,1,-1);
        Tile river2 = new River(1,-1,0);
        Tile river3 = new River(1,0,-1);
        Tile river4 = new River(-1,1,0);

        lineInfantry.getHex().getContactingHexes();

        Set<Hex> reachable = lineInfantry.getReachableHexes(2);

        Tile dummyTarget = new LineInfantry(0, -2, 2);
        Path path = lineInfantry.getPathTo(dummyTarget);
    }
}
