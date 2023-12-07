package ru.geekbrains.hexcore;


import javax.management.ConstructorParameters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Basic ABSTRACT class for all hex based entities like Units and Terrain, Strategic points
 */
public class Tile {
    private Hex hex = null;
    static Battlefield battlefield = Battlefield.getInstance();
    protected boolean passable;
    protected boolean blockLOS;

    //region CONSTRUCTORS
    private void init(){
        battlefield.putTile(this.getHex(), this);
    }
    protected Tile(int s, int q, int r) {
        this.hex = new Hex(s, q, r);
        init();
    }
    protected Tile(Hex hex) {
        this(hex.getS(), hex.getQ(), hex.getR());
    }
    //endregion

    public Hex getHex() {
        return hex;
    }
    public void setS(int s) {
        this.hex.setS(s);
    }
    public void setQ(int q) {
        this.hex.setQ(q);
    }
    public void setR(int r) {
        this.hex.setR(r);
    }
    public int getS() {
        return hex.getS();
    }
    public int getQ() {
        return hex.getQ();
    }
    public int getR() {
        return hex.getR();
    }

    public boolean isPassable() {
        return passable;
    }
    public void stepInEffect() {
        System.out.println(info());
    }

    protected void setCoordinate(Hex hex){
        setS(hex.getS());
        setQ(hex.getQ());
        setR(hex.getR());
    }

    public boolean move(Path path) {
        if (validatePath(path)) {
            for (Hex delta : path.hexList) {
                hex.add(delta);
            }
        }
        return  false;
    }

    public boolean validatePath(Path path) {
        return true;
    }

    /**
     * Get Set of Tiles that can be reached from this in range of movement
     * @param movement range
     * @return Set of Tiles that can be reached within specified distance
     */
    public Set<Hex> getReachableHexes(int movement){
        Set<Hex> visited = new HashSet<>();
        visited.add(this.getHex());
        List<List<Hex>> rounds = new ArrayList<>();
        int step = 0;
        rounds.add(new ArrayList<>());
        rounds.get(0).add(this.hex);
        while (step <= movement) {
            step++;
            rounds.add(new ArrayList<>());
            for (Hex hex : rounds.get(step - 1)) {
                List<Hex> neighbours = hex.getContactingHexes();
                for (Hex neighbour : neighbours) {
                    if (!visited.contains(neighbour) && battlefield.isPassable(neighbour)) {
                        visited.add(neighbour);
                        rounds.get(step).add(neighbour);
                    }
                }
            }
        }
        return visited;
    }
    public Set<Tile> getReachableTerrains(int movement) {
        Set<Hex> hexes = getReachableHexes(movement);
        Set<Tile> tiles = new HashSet<>();
        for (Hex hex1 : hexes) {
            tiles.add(battlefield.getTerrainByCoordinate(hex1));
        }
        return tiles;
    }

    /**
     * Get Path to target tile. Obtained by linear interpolation
     * @param to target Tile
     * @return Path to target Tile
     */
    public Path getLineOfSight(Tile to) {
        int dist = hex.findDistance(to.getHex());
        List<Hex> results = new ArrayList<>();
        for (int i = 0; i <= dist; i++) {
            Hex interpolatedHex =  Core.hexLinearInterpolation(hex, to.hex, 1.0/dist*i);
            results.add(new Hex(Core.roundHex(interpolatedHex.getS(), interpolatedHex.getQ(), interpolatedHex.getR())));
        }
        return new Path(results);
    }

    public boolean hasLOS(Tile to) {
        Path path = getLineOfSight(to);
        for (Hex hex : path.hexList) {
            if (battlefield.getTerrainByCoordinate(hex).blockLOS)
                return false;
        }
        return true;
    }

    /**
     * Returns string with coordinates information
     * @return String
     */
    public String info() {
        return String.format("Has coordinates (%d, %d, %d)", this.getS(), this.getQ(), this.getR());
    }

}