import ru.geekbrains.cnc.tiles.Forest;
import ru.geekbrains.cnc.tiles.LineInfantry;
import ru.geekbrains.cnc.tiles.River;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.RectangleMapInitializer;
import ru.geekbrains.hexcore.game.Player;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.tiles.Unit;
import ru.geekbrains.viewer.SwingBattlefieldPresenter;

import java.util.Set;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Battlefield.setDimensions(-4, 4, -6, 6); // C&C field
        Battlefield.setMapInitializer(new RectangleMapInitializer());

        SwingBattlefieldPresenter battlefieldDrawer = new SwingBattlefieldPresenter(Battlefield.getInstance());
        battlefieldDrawer.setGUI();

        Battlefield.getInstance().initializeMap();

        Player p1 = new Player("FirstPlayer");
        Player p2 = new Player("SecondPlayer");

        Unit lineInfantry = new LineInfantry(p1, new Hex(0, 0, 0));
        Tile forest1 = new Forest(0, 0, 0);
        Tile forest2 = new Forest(-2, 0, 2);

        Tile river1 = new River(0, 1, -1);
        Tile river2 = new River(1, -1, 0);
        Tile river3 = new River(1, 0, -1);
        Tile river4 = new River(-1, 1, 0);

        lineInfantry.getHex().getContactingHexes();

        Set<Hex> reachable = lineInfantry.getReachableHexes(2);

        Unit dummyTarget = new LineInfantry(p2, new Hex(0, -2, 2));
        Path path = lineInfantry.getPathTo(forest2);

        lineInfantry.move(path);
        lineInfantry.attack(dummyTarget);
    }
}
