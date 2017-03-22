package agent;

import level.Board;

import java.util.*;

/**
 * Created by Jonni on 3/20/2017.
 */

/* *****************************************************
 * TODO: BUG: occasionally adds bombs to pending *
 ***************************************************** */


public class PerspectiveBoard {

    // FOR DEBUGGING: TODO: REMOVE
    public static Board b = null;

    public static final byte UNKNOWN = -1;
    public static final byte BOMB = 10;

    private Map<Position, ConstraintInfo> constraintPositions;
    private byte[][] board;

    private Set<Position> containsBombSet;
    private Set<Position> removeSet;

    public PerspectiveBoard(int width, int height) {
        this.containsBombSet = new HashSet<>();
        this.removeSet = new HashSet<>();
        this.constraintPositions = new HashMap<>();
        board = new byte[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = UNKNOWN;
            }
        }
    }

    public void setBombAt(int x, int y, PositionGrid grid, Set<Position> moves, Set<Position> bombs) {
        board[x][y] = BOMB;
        bombs.add(grid.getVariable(x,y));
        for (Position position : grid.getNeighbours(x,y)) {
            ConstraintInfo info;
            if ((info = constraintPositions.get(position)) != null) {
                info.decrementAdjacentBombs();
                info.removeVariable(grid.getVariable(x, y));
                storeSimplifications(info, position, moves);
            }
        }
    }

    public void setAdjacent(int x, int y, int adjacent, PositionGrid grid, Set<Position> moves, Set<Position> bombs) {
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
                    storeSimplifications(info, position, moves);
                }
            }
        }
        if (neighbours.size() == adjacent) {
            for (Position position : neighbours) {
                containsBombSet.add(position);
            }
        }
        else if (adjacent == 0) moves.addAll(neighbours);
        else this.constraintPositions.put(grid.getVariable(x, y), new ConstraintInfo(neighbours, adjacent));

        emptyTempSets(grid, moves, bombs);
    }

    public Map<Position, ConstraintInfo> getConstraintPositions() {
        return this.constraintPositions;
    }

    public byte[][] getBoard() {
        return this.board;
    }

    private void emptyTempSets(PositionGrid grid, Set<Position> moves, Set<Position> bombs) {
        while (!containsBombSet.isEmpty()) {
            Iterator<Position> it = containsBombSet.iterator();
            Position bomb = it.next();
            it.remove();
            if (this.board[bomb.getX()][bomb.getY()] != PerspectiveBoard.BOMB) {
                setBombAt(bomb.getX(), bomb.getY(), grid, moves, bombs);
            }
        }
        for (Position pos : this.removeSet) {
            this.constraintPositions.remove(pos);
        }
    }

    private void storeSimplifications(ConstraintInfo info, Position position, Set<Position> moves) {
        if (info.isEmpty()) {
            this.removeSet.add(position);
        } else if (info.noBombs()) {
            moves.addAll(info.getUnknownNeighbours());
            this.removeSet.add(position);
        } else if (info.allBombs()) {
            for (Position pos : info.getUnknownNeighbours()) this.containsBombSet.add(pos);
            this.removeSet.add(position);
        }
    }
}