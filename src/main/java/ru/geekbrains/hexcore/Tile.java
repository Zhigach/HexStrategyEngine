package ru.geekbrains.hexcore;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.TileTypes.Unit;

import java.util.*;


/**
 * Basic ABSTRACT class for all hex based entities like Units and Terrain, Strategic points. All Tiles when instantiated are automatically added to the Battlefield (singleton)
 */
@Slf4j
public abstract class Tile {
    @Getter
    @Setter
    protected Hex hex;
    final static Battlefield battlefield = Battlefield.getInstance();
    protected boolean passable = true;
    protected boolean blockLOS;
    protected boolean enteringUnitMustStop = false;

    //region CONSTRUCTORS

    /**
     * Method adding ANY new Tile onto the Battlefield.
     * Multiple Battlefields not supported by this core.
     */
    private void init(){
        log.info(String.format("New Tile %s created. Putting it onto the Battlefield", this.getClass().getSimpleName()));
        battlefield.putTile(this.getHex(), this);
    }
    protected Tile(int s, int q, int r) {
        this.hex = new Hex(s, q, r);
        init();
    }
    protected Tile(@org.jetbrains.annotations.NotNull Hex hex) {
        this(hex.getS(), hex.getQ(), hex.getR());
    }
    //endregion

    /**
     * Get Battlefield this Tile is assigned to
     * @return Battlefield
     */
    public Battlefield getBattlefield() {
        return battlefield;
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
     * Tells us if this Tile is passable in single turn.
     * @return bool value
     */
    public boolean isPassable(boolean passableOnly) {
        return passable && passableOnly || passable && !enteringUnitMustStop;
    }
    public boolean isPassable() {
        return isPassable(false);
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
                    if (!visited.contains(neighbour) && battlefield.isPassable(neighbour)) {
                        visited.add(neighbour);
                        rounds.get(step).add(neighbour);
                    }
                }
            }
        }
        log.debug(String.format("Requested reachable hexes for %s. Returning %s", this, visited));
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
        HashMap<Hex, Hex> cameFrom = new HashMap<>(); // key is where, value is from
        frontier.add(this.getHex());

        boolean targetReached = false;
        Set<Hex> reached = new HashSet<>();
        reached.add(this.getHex());
        while (!frontier.isEmpty()) {
            Hex current =  frontier.poll();
            List<Hex> neighbours = current.getContactingHexes();
            for (Hex neighbour : neighbours) {
                if (battlefield.isPassable(neighbour) && !reached.contains(neighbour)) {
                    frontier.add(neighbour);
                    reached.add(neighbour);
                    if (!cameFrom.containsKey(neighbour)) {
                        cameFrom.put(neighbour, current);
                        reached.add(neighbour);
                    }
                    if (neighbour.equals(destination.getHex())) {
                        targetReached = true;
                        break;
                    }
                }
            }
            if (targetReached) {
                break;
            }
        }

        Hex current = destination.getHex();
        while (current != this.getHex()) {
            Hex previous = cameFrom.get(current);
            result.addStep(current.getDelta(previous));
            current = previous;
        }
        result.revert();
        log.debug(String.format("Path requested from %s -> %s. Returning %s", this, destination, result));
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
        log.debug(String.format("Reachable Terrains requested for %s. Returning %s", this, tiles));
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
        Path path = new Path(results);
        log.debug(String.format("Line of sight from %s -> %s requested. Returning %s", this, to, path));
        return path;
    }

    /**
     * Check does this Tile has line of sight to the destination Tile.
     * @param to destination Tile
     * @return bool
     */
    public boolean hasLOS(Tile to) {
        Path path = getLineOfSight(to);
        for (Hex hex : path.hexList) {
            if (battlefield.getTerrainByCoordinate(hex).blockLOS) {
                log.debug(String.format("%s has LOS to %s: false", this, to));
                return false;
            }
        }
        log.debug(String.format("%s has LOS to %s: true", this, to));
        return true;
    }

    /**
     * Returns string with coordinates information
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s has coordinates %s", this.getClass().getName(), hex);
    }
}