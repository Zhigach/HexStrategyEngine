package ru.geekbrains.hexcore;


import ru.geekbrains.hexcore.TileTypes.Unit;

import javax.management.Query;
import java.util.*;


/**
 * Basic ABSTRACT class for all hex based entities like Units and Terrain, Strategic points
 */
public abstract class Tile {
    protected Hex hex;
    static Battlefield battlefield = Battlefield.getInstance();
    protected boolean passable;
    protected boolean blockLOS;

    //region CONSTRUCTORS

    /**
     * Method adding ANY new Tile onto the Battlefield.
     * Multiple Battlefields not supported by this core.
     */
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

    /**
     * get Tile hex coordinate
     * @return Hex coordinate
     */
    public Hex getHex() {
        return hex;
    }
    /**
     * Get Battlefield this Tile is assigned to
     * @return Battlefield
     */
    public Battlefield getBattlefield() {
        return battlefield;
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

    /**
     * Abstract method to implement at siblings.
     * @param unit Unit that steps at this Tile
     */
    public abstract void stepInEffect(Unit unit);

    /**
     * Arbitrary effect to be implemented at siblings
     */
    public abstract void stepInEffect();

    /**
     * Abstract method to implement at siblings.
     * @param unit Unit that steps out of this Tile
     */
    public abstract void stepOutEffect(Unit unit);
    /**
     * Arbitrary effect to be implemented at siblings
     */
    public abstract void stepOutEffect();

    /**
     * Tells us if this Tile is passable.
     * @return bool value
     */
    public boolean isPassable() {
        return passable;
    }

    /**
     * Set Tile coordinate explicitly
     * @param hex new coordinate
     */
    protected void setCoordinate(Hex hex){
        this.hex.setS(hex.getS());
        this.hex.setQ(hex.getQ());
        this.hex.setR(hex.getR());
    }

    /**
     * Check if the provided path is available for this specific Unit (Tile in general).
     * @param path path to be checked
     * @return bool
     */
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
        while (step < movement) {
            step++;
            rounds.add(new ArrayList<>());
            for (Hex hex : rounds.get(step - 1)) {
                List<Hex> neighbours = hex.getContactingHexes();
                for (Hex neighbour : neighbours) {
                    if (!visited.contains(neighbour) && battlefield.isPassable(neighbour)) { // TODO: take into account that tiles can be passable but forcing stepping in Unit to stop
                        visited.add(neighbour);
                        rounds.get(step).add(neighbour);
                    }
                }
            }
        }
        return visited;
    }

    /**
     * Basic method to get Path to destination hex
     * @param destination Tile of interest - final point of the way
     * @return deltas that should be added to current coordinate in order to get to destination
     */
    public Path getPathTo(Tile destination) {
        Path result = new Path();
        LinkedList<Hex> frontier = new LinkedList<>();
        HashMap<Hex, Hex> cameFrom = new HashMap<>();
        frontier.add(this.getHex());
        
        while (!frontier.isEmpty()) {
            Hex current =  frontier.poll();
            List<Hex> neighbours = current.getContactingHexes();
        }

        return result;
    }

    /**
     * Get all Terrains that can be reached from this Tile using provided movement range.
     * @param movement integer movement
     * @return Set of Tiles
     */
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

    /**
     * Check does this Tile has line of sight to the destination Tile.
     * @param to destination Tile
     * @return bool
     */
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
        return String.format("%s has coordinates (%d, %d, %d)", this.getClass().getName(), this.getS(), this.getQ(), this.getR());
    }
}