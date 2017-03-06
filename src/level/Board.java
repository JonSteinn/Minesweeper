package level;

import java.util.Arrays;

/**
 * Created by Jonni on 3/5/2017.
 */
public class Board {
    private boolean[][] board;
    private int bombCount;

    public Board(int width, int height) {
        this.bombCount = 0;
        this.board = new boolean[height][width];
    }

    public int getWidth() {
        return this.board[0].length;
    }

    public int getHeight() {
        return this.board.length;
    }

    public int getBombCount() {
        return this.bombCount;
    }

    public boolean containsBomb(int x, int y) {
        return board[y][x];
    }

    public boolean outOfBounds(int x, int y) {
        return x < 0 || y < 0 || x >= this.getWidth() || y >= this.getHeight();
    }

    public void addBomb(int x, int y) {
        if (!board[y][x]) bombCount++;
        board[y][x] = true;
    }

    @Override
    public String toString() {
        StringBuffer str = new StringBuffer();
        for (int j = 0; j < getHeight(); j++) {
            for (int i = 0; i < getWidth(); i++) {
                str.append(containsBomb(i, j) ? '1' : '0');
            }
            str.append('\n');
        }
        return str.toString();
    }

    @Override
    public boolean equals(Object o) {
        return Arrays.deepEquals(this.board, ((Board)o).board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }

}