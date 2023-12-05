import ru.geekbrains.cnc.tiles.Unit;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.HexVector;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.Tile;


public class Main {

    public static void main(String[] args) {
        Tile tile1 = new Unit(0, 0, 0);
        Tile tile2 = new Unit(-2, 0, 2);

        Battlefield battlefield = Battlefield.getInstance();
        System.out.println("get");
    }
}
