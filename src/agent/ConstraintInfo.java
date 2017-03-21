package agent;

import java.util.Set;

/**
 * Created by Jonni on 3/20/2017.
 */
public class ConstraintInfo {

    private Set<Position> unknownNeighbours;
    private int adjacentBombs;

    public ConstraintInfo(Set<Position> unknownNeighbours, int adjacentBombs) {
        this.unknownNeighbours = unknownNeighbours;
        this.adjacentBombs = adjacentBombs;
    }

    public int getAdjacentBombs() {
        return this.adjacentBombs;
    }

    public void decrementAdjacentBombs() {
        this.adjacentBombs--;
    }

    public Set<Position> getUnknownNeighbours() {
        return this.unknownNeighbours;
    }

    public void removeVariable(Position position) {
        this.unknownNeighbours.remove(position);
    }

    public boolean allBombs() {
        return this.adjacentBombs == unknownNeighbours.size();
    }

    public boolean noBombs() {
        return this.adjacentBombs == 0;
    }

    public boolean isEmpty() {
        return this.unknownNeighbours.isEmpty();
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
