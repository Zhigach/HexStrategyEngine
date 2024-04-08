package ru.geekbrains.hexcore;

import lombok.Getter;
import ru.geekbrains.hexcore.model.Hex;
import ru.geekbrains.hexcore.utils.HexDelta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * List of Hex delta vectors with additional methods and validators.
 */
@Getter
public class Path {
    List<Hex> hexList;

    public Path() {
        this.hexList = new ArrayList<>();
    }

    /**
     * List wrapper class. Designed to contain only enum form HexDeltas (single steps), not absolute coordinates
     *
     * @param hexList initial List object (can be empty). Method contains validation
     */
    public Path(List<Hex> hexList) {
        if (!isValidDeltaList(hexList))
            throw new IllegalArgumentException("Provided path is invalid. Only deltas can be used");
        this.hexList = hexList;
    }

    /**
     * Add step (Hex coordinate delta) to the existing Path. Passed parameter validated first.
     *
     * @param hexDelta single step delta in hex coordinates
     */
    public void addStep(Hex hexDelta) {
        if (HexDelta.isValidDelta(hexDelta)) {
            this.hexList.add(hexDelta);
        } else {
            throw new IllegalArgumentException("Invalid hex delta");
        }
    }

    /**
     * Reverse the deltas list (hexList)
     */
    public void revert() {
        Collections.reverse(hexList);
    }

    private static boolean isValidDeltaList(List<Hex> hexList) {
        return HexDelta.isValidDeltaList(hexList);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Path: {");
        hexList.forEach(hexDelta -> sb.append(String.format("[%s,%s,%s],", hexDelta.getS(), hexDelta.getQ(), hexDelta.getR())));
        sb.deleteCharAt(sb.length() - 1);
        return sb.toString() + "}";
    }
}
