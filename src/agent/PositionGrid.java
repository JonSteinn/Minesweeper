package agent;

import java.util.ArrayList;
import java.util.stream.IntStream;

/**
 * Created by Jonni on 3/20/2017.
 */
public class PositionGrid {
    private Position[][] board;

    public PositionGrid(int width, int height) {
        this.board = IntStream.range(0, width).mapToObj(x ->
                IntStream.range(0, height).mapToObj(y ->
                        new Position(x, y)).toArray(Position[]::new)
        ).toArray(Position[][]::new);
    }

    public Position getVariable(int x, int y) {
        return this.board[x][y];
    }

    public ArrayList<Position> getNeighbours(int x, int y) {
        ArrayList<Position> returnValue = new ArrayList<>();
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if ((i != x || j != y) && j >= 0 && i >= 0 && i < this.board.length && j < this.board[0].length) {
                    returnValue.add(this.board[i][j]);
                }
            }
        }
        return returnValue;
    }
}
