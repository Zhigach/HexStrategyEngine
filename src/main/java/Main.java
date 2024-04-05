import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.RectangleMapInitializer;
import ru.geekbrains.hexcore.core.GameEngine;
import ru.geekbrains.hexcore.core.Player;
import ru.geekbrains.hexcore.model.Tile;
import ru.geekbrains.hexcore.model.Unit;
import ru.geekbrains.hexcore.tiles.terrain.Forest;
import ru.geekbrains.hexcore.tiles.terrain.River;
import ru.geekbrains.hexcore.tiles.unit.LineInfantry;
import ru.geekbrains.hexcore.utils.Hex;
import ru.geekbrains.viewer.SwingBattlefieldPresenter;

import java.util.List;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        Battlefield battlefield = Battlefield.getInstance();

        Battlefield.setDimensions(-4, 4, -6, 6); // C&C field
        Battlefield.setMapInitializer(new RectangleMapInitializer());

        SwingBattlefieldPresenter battlefieldDrawer = new SwingBattlefieldPresenter(battlefield);
        battlefieldDrawer.setGUI();

        battlefield.initializeMap();

        GameEngine gameEngine = new GameEngine(
                List.of(new Player("FirstPlayer"), new Player("SecondPlayer")), battlefield
        );

        Tile forest1 = new Forest(0, 0, 0);
        Tile forest2 = new Forest(-2, 0, 2);

        Tile river1 = new River(0, 1, -1);
        Tile river2 = new River(1, -1, 0);
        Tile river3 = new River(1, 0, -1);
        Tile river4 = new River(-1, 1, 0);

        Unit lineInfantry = new LineInfantry(gameEngine.getPlayers().get(0), new Hex(0, 0, 0));
        Unit dummyTarget = new LineInfantry(gameEngine.getPlayers().get(1), new Hex(0, -2, 2));

        Path path = lineInfantry.getPathTo(forest2);

        gameEngine.moveUnit(lineInfantry, path);
        gameEngine.attack(lineInfantry, dummyTarget);
    }
}
