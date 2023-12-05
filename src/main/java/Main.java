import ru.geekbrains.cnc.tiles.River;
import ru.geekbrains.cnc.tiles.Unit;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.HexVector;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.Tile;


public class Main {

    public static void main(String[] args) {
        Tile tile1 = new Unit(0, 0, 0);
        Tile tile2 = new Unit(-2, 0, 2);
        Tile tile3 = new Unit(-2, 0, 2);

        Tile river1 = new River(0,1,-1);
        Tile river2 = new River(1,-1,0);
        Tile river3 = new River(1,0,-1);

        Battlefield battlefield = Battlefield.getInstance();
        System.out.println("get");
    }
}
