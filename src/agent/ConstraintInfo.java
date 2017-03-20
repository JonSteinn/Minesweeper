package agent;

import java.util.Set;

/**
 * Created by Jonni on 3/20/2017.
 */
public class ConstraintInfo {
    public Set<Position> unknownNeighbours;
    public int adjacentBombs;

    public ConstraintInfo(Set<Position> unknownNeighbours, int adjacentBombs) {
        this.unknownNeighbours = unknownNeighbours;
        this.adjacentBombs = adjacentBombs;
    }

    @Override
    public boolean equals(Object o) {
        return this.unknownNeighbours.equals(((ConstraintInfo)o).unknownNeighbours);
    }

    @Override
    public int hashCode() {
        return this.unknownNeighbours.hashCode();
    }

    @Override
    public String toString() {
        return "[" + adjacentBombs + ", " + unknownNeighbours.toString() + "]";
    }
}
