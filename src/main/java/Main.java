import ru.geekbrains.cnc.tiles.Unit;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.Tile;


public class Main {

    public static void main(String[] args) {
        Tile tile1 = new Unit(0, 0, 0);
        Tile tile2 = new Unit(-2, 0, 2);

        Path path = tile1.getLineOfSight(tile2);
        tile1.move(path);
        System.out.println(tile1.findDistance(tile2));
    }
}
