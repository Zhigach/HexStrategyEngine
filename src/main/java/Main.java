import ru.geekbrains.cnc.tiles.Forest;
import ru.geekbrains.cnc.tiles.LineInfantry;
import ru.geekbrains.cnc.tiles.River;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Tile;


public class Main {

    public static void main(String[] args) {

        Tile tile2 = new LineInfantry(-2, 0, 2);
        Tile tile3 = new Forest(-2, 0, 2);

//        Tile river1 = new River(0,1,-1);
//        Tile river2 = new River(1,-1,0);
//        Tile river3 = new River(1,0,-1);

        Battlefield battlefield = Battlefield.getInstance();
        System.out.println("get");
    }
}
