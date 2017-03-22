package agent;

import java.util.*;

/**
 * Created by Jonni on 3/20/2017.
 */

/* *****************************************************
 * TODO: BUG: removes from collection in mid-iteration *
 ***************************************************** */


public class PerspectiveBoard {

    public static final byte UNKNOWN = -1;
    public static final byte BOMB = 10;

    private Map<Position, ConstraintInfo> constraintPositions;
    private byte[][] board;

    public PerspectiveBoard(int width, int height) {
        this.constraintPositions = new HashMap<>();
        board = new byte[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = UNKNOWN;
            }
        }
    }

    public void setBombAt(int x, int y, PositionGrid grid, Set<Position> moves) {
        board[x][y] = BOMB;
        for (Position position : grid.getNeighbours(x,y)) {
            ConstraintInfo info;
            if ((info = constraintPositions.get(position)) != null) {
                info.decrementAdjacentBombs();
                info.removeVariable(grid.getVariable(x, y));
                simplify(info, position, moves, grid);
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
                    info.removeVariable(grid.getVariable(x, y));
                    simplify(info, position, moves, grid);
                }
            }
        }
        if (neighbours.size() == adjacent) {
            for (Position position : neighbours) {
                setBombAt(position.getX(), position.getY(), grid, moves);
            }
        }
        else if (adjacent == 0) moves.addAll(neighbours);
        else this.constraintPositions.put(grid.getVariable(x, y), new ConstraintInfo(neighbours, adjacent));
    }

    public Map<Position, ConstraintInfo> getConstraintPositions() {
        return this.constraintPositions;
    }

    public byte[][] getBoard() {
        return this.board;
    }

    private void simplify(ConstraintInfo info, Position position, Set<Position> moves, PositionGrid grid) {
        if (info.isEmpty()) this.constraintPositions.remove(position);
        else if (info.noBombs()) {
            moves.addAll(info.getUnknownNeighbours()); // 2x check
            this.constraintPositions.remove(position);
        }
        else if (info.allBombs()) {
            for (Position pos : info.getUnknownNeighbours()) {
                setBombAt(pos.getX(), pos.getY(), grid, moves); // 2x check
            }
            this.constraintPositions.remove(position);
        }
    }
}
