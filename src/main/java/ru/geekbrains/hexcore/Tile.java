package ru.geekbrains.hexcore;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.min;
import static java.lang.Math.max;


/**
 * Basic ABSTRACT class for all hex based entities like Units and Terrain, Strategic points
 */
public class Tile extends HexVector{
    boolean passable;
    boolean blockLOS;
    protected Tile(int s, int q, int r) {
        super(s,q,r);
    }
    protected Tile(HexVector hexVector) {
        super(hexVector.getS(), hexVector.getQ(), hexVector.getR());
    }

    public void stepInEffect() {
        System.out.println(info());
    }

    protected void setCoordinate(HexVector hexVector){
        setS(hexVector.getS());
        setQ(hexVector.getQ());
        setR(hexVector.getR());
    }

    /**
     * Returns integer range from one hex to another
     * @param to destination Tile
     * @return rounded integer range between the given hexes
     */
    public int findDistance(Tile to) {
        return (abs(this.getQ() - to.getQ())
                + abs(this.getR() - to.getR())
                + abs(this.getS() - to.getS()))
                / 2;
    }

    /**
     * Method returns hex delta that must be added to this in order to get to the to
     * @param to destination hes
     * @return delta that must be added to this in order to get to the to
     */
    private HexVector getDelta(Tile to) {
        return new HexVector(to.getS() - this.getS(), to.getQ() - this.getQ(), to.getR() - this.getR());
    }

    /**
     * Returns a list of contacting hexes
     * @return list of contacting hexes regardless of their properties
     */
    public List<HexVector> getContactingHexes() {
        List<HexVector> result = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            result.add(this.add(HexDeltas.HEX_DELTAS.get(i)));
        }
        return result;
    }

    /**
     * Get all hexes in a specified range. Includes the origin hex itself
     * @param range range within
     * @return List of hexes within range
     */
    public List<HexVector> getSurroundingHexes(int range) {
        List<HexVector> results = new ArrayList<>();
        for (int q = -range; q <= range; q++){
            int lowLimit = max(-range, -q - range);
            int upperLimit = min(range, -q + range);
            for (int r = lowLimit; r <= upperLimit; r++) {
                int s = -q - r;
                results.add(new HexVector(s, q, r));
            }
        }
        return results;
    }

    public boolean move(Path path) {
        /**
         * Проходим все гексы по очереди. На каждом из них могут быть эффекты
         */
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

    public Path searchPath(HexVector from, HexVector to){
        Path result = new Path();
        /*
        function hex_reachable(start, movement):
            var visited = set() # set of hexes
            add start to visited
            var fringes = [] # array of arrays of hexes
            fringes.append([start])

            for each 1 < k ≤ movement:
                fringes.append([])
                for each hex in fringes[k-1]:
                    for each 0 ≤ dir < 6:
                        var neighbor = hex_neighbor(hex, dir)
                        if neighbor not in visited and not blocked:
                            add neighbor to visited
                            fringes[k].append(neighbor)

            return visited
         */
        return result;
    }

    //TODO: должно возвращать наличие/отсутствие линии видимости
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
            //if (hexVector.)
        }
        return  false;
    }


}