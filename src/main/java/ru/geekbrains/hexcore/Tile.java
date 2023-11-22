package ru.geekbrains.hexcore;


import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Basic ABSTRACT class for all hex based entities like Units and Terrain, Strategic points
 */
public class Tile {
    private HexVector coordinate;
    boolean passable;
    protected Tile(int s, int q, int r) {
        this.coordinate = new HexVector(s,q,r);
    }
    protected Tile(HexVector hexVector) {
        this.coordinate = new HexVector(hexVector.getS(), hexVector.getQ(), hexVector.getR());
    }

    public void stepInEffect() {
        System.out.println(getInfo());
    }

    public String getInfo() {
        return coordinate.info();
    }

    public HexVector getCoordinate() {
        return coordinate;
    }
    protected void setCoordinate(HexVector hexVector){
        this.coordinate = hexVector;
    }
    protected void step(HexVector delta) {
        this.setCoordinate(coordinate.add(delta));
    }

    /**
     * Returns integer range from one hex to another
     * @param to destination Tile
     * @return rounded integer range between the given hexes
     */
    public int findDistance(Tile to) {
        return (abs(this.coordinate.getQ() - to.coordinate.getQ())
                + abs(this.coordinate.getR() - to.coordinate.getR())
                + abs(this.coordinate.getS() - to.coordinate.getS()))
                / 2;
    }

    /**
     * Method returns hex delta that must be added to this in order to get to the to
     * @param to destination hes
     * @return delta that must be added to this in order to get to the to
     */
    private HexVector getDelta(HexVector to) {
        return new HexVector(to.getS() - this.coordinate.getS(), to.getQ() - this.coordinate.getQ(), to.getR() - this.coordinate.getR());
    }

    /**
     * Returns a list of contacting hexes
     * @return list of contacting hexes regardless of their properties
     */
    public List<HexVector> getContactingHexes() {
        List<HexVector> result = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            result.add(this.coordinate.add(HexDeltas.HEX_DELTAS.get(i)));
        }
        return result;
    }

    public boolean move(Path path) {
        /**
         * Проходим все гексы по очереди. На каждом из них могут быть эффекты
         */
        if (validatePath(this.getCoordinate(), path)) {
            for (HexVector delta : path.hexList) {
                this.step(delta);
            }
        }
        return  false;
    }

    public boolean validatePath(HexVector hexVector, Path path) {
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
            HexVector interpolatedHex =  Core.hexLinearInterpolation(this.coordinate, to.coordinate, 1.0/dist*i);
            results.add(new HexVector(Core.roundHexVector(interpolatedHex.getS(), interpolatedHex.getQ(), interpolatedHex.getR())));
        }
        return new Path(results);
    }


}