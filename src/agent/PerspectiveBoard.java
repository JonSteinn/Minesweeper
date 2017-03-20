package agent;

import java.util.*;

/**
 * Created by Jonni on 3/20/2017.
 */
public class PerspectiveBoard {

    public static final byte UNKNOWN = -1;
    public static final byte BOMB = 10;

    public Map<Position, ConstraintInfo> constraintPositions;
    public byte[][] board;

    public PerspectiveBoard(int width, int height) {
        this.constraintPositions = new HashMap<>();
        board = new byte[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = UNKNOWN;
            }
        }
    }

    public void setBombAt(int x, int y, PositionGrid grid) {
        board[x][y] = BOMB;
        for (Position position : grid.getNeighbours(x,y)) {
            ConstraintInfo info;
            if ((info = constraintPositions.get(position)) != null) {
                info.adjacentBombs--;
                info.unknownNeighbours.remove(grid.getVariable(x,y));
                if (info.unknownNeighbours.isEmpty()) this.constraintPositions.remove(position);
            }
        }
    }

    public void setAdjacent(int x, int y, int adjacent, PositionGrid grid, Set<Position> moves) {
        board[x][y] = (byte)adjacent;
        Set<Position> neighbours = new HashSet<>();
        for (Position position : grid.getNeighbours(x,y)) {
            if (board[position.getX()][position.getY()] == UNKNOWN) {
                neighbours.add(position);
            } else if (board[position.getX()][position.getY()] == BOMB) {
                adjacent--;
            } else {
                ConstraintInfo info;
                if ((info = constraintPositions.get(position)) != null) {
                    info.unknownNeighbours.remove(grid.getVariable(x, y));
                    if (info.unknownNeighbours.isEmpty()) this.constraintPositions.remove(position);
                }
            }
        }
        if (neighbours.size() == adjacent) {
            for (Position position : neighbours) {
                setBombAt(position.getX(), position.getY(), grid);
            }
        } else if (adjacent == 0) {
            moves.addAll(neighbours);
        } else{
            this.constraintPositions.put(grid.getVariable(x, y), new ConstraintInfo(neighbours, adjacent));
        }
    }

    public Set<Position> getVariablesForConstraint(Position position) {
        return this.constraintPositions.get(position).unknownNeighbours;
    }
}
