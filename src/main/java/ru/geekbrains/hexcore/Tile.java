package ru.geekbrains.hexcore;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Basic ABSTRACT class for all hex based entities like Units and Terrain, Strategic points
 */
public class Tile extends HexVector{
    static Battlefield battlefield = Battlefield.getInstance();
    protected boolean passable;
    protected boolean blockLOS;
    protected Tile(int s, int q, int r) {
        super(s,q,r);
        battlefield.putTile((HexVector) this, this);
    }
    protected Tile(HexVector hexVector) {
        super(hexVector.getS(), hexVector.getQ(), hexVector.getR());
        battlefield.putTile((HexVector) this, this);
    }
    public boolean isPassable() {
        return passable;
    }
    public void stepInEffect() {
        System.out.println(info());
    }

    protected void setCoordinate(HexVector hexVector){
        setS(hexVector.getS());
        setQ(hexVector.getQ());
        setR(hexVector.getR());
    }

    public boolean move(Path path) {
        if (validatePath(path)) {
            for (HexVector delta : path.hexList) {
                this.add(delta);
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
    public Set<HexVector> getReachableTiles(int movement){
        Set<HexVector> visited = new HashSet<>();
        visited.add(this);
        List<List<HexVector>> fringes = new ArrayList<>();

        int step = 0;
        while (step <= movement) {
            step++;
            fringes.add(new ArrayList<>());
            for (HexVector hexVector : fringes.get(step - 1)) {
                List<HexVector> neighbours = hexVector.getContactingHexes();
                for (HexVector neighbour : neighbours) {
                    if (!visited.contains(neighbour) &&
                            battlefield.getTileByCoordinate(hexVector).get(0).isPassable()) {
                        visited.add(neighbour);
                        fringes.get(step).add(neighbour);
                    }
                }
            }
        }
        return visited;
    }

    /**
     * Get Path to target tile. Obtained by linear interpolation
     * @param to target Tile
     * @return Path to target Tile
     */
    public Path getLineOfSight(Tile to) {
        int dist = this.findDistance(to);
        List<HexVector> results = new ArrayList<>();
        for (int i = 0; i <= dist; i++) {
            HexVector interpolatedHex =  Core.hexLinearInterpolation(this, to, 1.0/dist*i);
            results.add(new HexVector(Core.roundHexVector(interpolatedHex.getS(), interpolatedHex.getQ(), interpolatedHex.getR())));
        }
        return new Path(results);
    }

    public boolean hasLOS(Tile to) {
        Path path = getLineOfSight(to);
        for (HexVector hexVector : path.hexList) {
            if (battlefield.getTileByCoordinate(hexVector).get(0).blockLOS)
                return false;
        }
        return true;
    }


}