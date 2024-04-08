package ru.geekbrains.hexcore.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import ru.geekbrains.hexcore.Battlefield;
import ru.geekbrains.hexcore.Path;
import ru.geekbrains.hexcore.model.interfaces.DrawableTile;
import ru.geekbrains.hexcore.utils.HexMath;

import java.awt.*;
import java.util.List;
import java.util.*;

import static java.lang.Math.sqrt;


/**
 * Basic ABSTRACT class for all hex based entities like Units and Terrain, Strategic points. All Tiles when instantiated are automatically added to the Battlefield (singleton)
 */
@NoArgsConstructor
@Slf4j
public abstract class Tile implements DrawableTile {

    @Getter
    @Setter
    protected Hex hex;
    final static protected Battlefield battlefield = Battlefield.getInstance();
    protected boolean passable = true;
    protected boolean blockLOS;
    protected boolean enteringUnitMustStop = false;
    protected Color FILL_COLOR = Color.WHITE;


    /**
     * Method adding ANY new Tile onto the Battlefield.
     * Multiple Battlefields not supported by this core.
     */
    private void init() {
        log.info(String.format("New Tile %s created. Putting it onto the Battlefield", this.getClass().getSimpleName()));
        battlefield.putTile(this.getHex(), this);
    }

    public Tile(int s, int q, int r) {
        this.hex = new Hex(s, q, r);
        init();
    }

    public Tile(Hex hex) {
        this(hex.getS(), hex.getQ(), hex.getR());
    }

    /**
     * Get Battlefield this Tile is assigned to
     *
     * @return Battlefield
     */
    public Battlefield getBattlefield() {
        return battlefield;
    }

    /**
     * Tells us if this Tile is passable in single turn.
     *
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
     *
     * @param path path to be checked
     * @return bool
     */
    public boolean validatePath(Path path) {
        return true;
    }

    /**
     * Get Set of Tiles that can be reached from this in specified movement range
     *
     * @param movement range
     * @return Set of Tiles that can be reached within specified distance
     */
    public Set<Hex> getReachableHexes(int movement) {
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
     * Basic method to get the shortest Path to destination hex
     *
     * @param destination Tile of interest - final point of the way
     * @return Path containing deltas that should be added to current coordinate in order to get to destination
     */
    public Path getPathTo(Tile destination) {
        log.debug("Path from {} to {} requested", this, destination);
        Hex destinationHex = destination.getHex();
        Path result = new Path();
        LinkedList<Hex> frontier = new LinkedList<>();
        HashMap<Hex, Hex> cameFrom = new HashMap<>(); // key is where, value is from
        frontier.add(this.getHex());

        boolean targetReached = false;
        Set<Hex> reached = new HashSet<>();
        reached.add(this.getHex());
        while (!frontier.isEmpty()) {
            Hex current = frontier.poll();
            List<Hex> neighbours = current.getContactingHexes();
            for (Hex neighbour : neighbours) {
                if (battlefield.isPassable(neighbour) && !reached.contains(neighbour)) {
                    frontier.add(neighbour);
                    reached.add(neighbour);
                    if (!cameFrom.containsKey(neighbour)) {
                        cameFrom.put(neighbour, current);
                        reached.add(neighbour);
                    }
                }
                if (neighbour.equals(destinationHex)) {
                    targetReached = true;
                    destinationHex = current;
                    break;
                }
            }
            if (targetReached) {
                break;
            }
        }

        if (!targetReached) {
            log.error("Target tile {} can't be reached from {}", destination, this);
            throw new RuntimeException("Target tile can't be reached");
        } else {
            Hex current = destinationHex;
            while (current != this.getHex()) {
                Hex previous = cameFrom.get(current);
                result.addStep(previous.getDelta(current));
                current = previous;
            }
            result.revert();
            log.debug("Path requested from {} -> {}. Returning {}", this, destination, result);
            return result;
        }
    }

    /**
     * Get all Terrains that can be reached from this Tile using provided movement range.
     *
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
     *
     * @param to target Tile
     * @return Path to target Tile
     */
    public Path getLineOfSight(Tile to) {
        int dist = hex.findDistance(to.getHex());
        List<Hex> results = new ArrayList<>();
        Hex previousHex = this.getHex();
        for (int i = 1; i <= dist; i++) {
            Hex interpolatedHex = HexMath.hexLinearInterpolation(hex, to.hex, 1.0 / dist * i);
            //Hex nextPoint = new Hex(HexMath.roundHex(interpolatedHex.getS(), interpolatedHex.getQ(), interpolatedHex.getR()));
            results.add(previousHex.getDelta(interpolatedHex));
            previousHex = interpolatedHex;
        }
        Path path = new Path(results);
        log.debug(String.format("Line of sight from %s -> %s requested. Returning %s", this, to, path));
        return path;
    }

    /**
     * Check does this Tile has line of sight to the destination Tile.
     *
     * @param to destination Tile
     * @return bool
     */
    public boolean hasLOS(Tile to) {
        Path path = getLineOfSight(to);
        for (Hex hex : path.getHexList()) {
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
     *
     * @return String
     */
    @Override
    public String toString() {
        return String.format("%s@%s", this.getClass().getSimpleName(), hex);
    }

    /**
     * Method to draw Tile
     */
    @Override
    public void draw(Graphics2D g2, int size, Point centerPoint) {
        g2.drawString(
                String.format(String.valueOf(
                        this.getHex())), (int) (centerPoint.x - sqrt(3) * size / 2), centerPoint.y);
    }
}