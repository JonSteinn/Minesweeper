package gui;

import level.Board;

/**
 * Created by jonsteinn on 6.3.2017.
 */
public class BoardButtons {
    private MineButton[][] buttons;
    private Board board;

    public BoardButtons(Board board) {
        this.board = board;
        this.buttons = new MineButton[board.getWidth()][board.getHeight()];
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                buttons[i][j] = new MineButton(adjacentMines(i ,j), board.containsBomb(i, j));
            }
        }
    }

    public MineButton getButton(int x, int y) {
        return buttons[x][y];
    }

    private int adjacentMines(int x, int y) {
        int counter = 0;
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (i >= 0 && i < this.board.getWidth() && j >= 0 && j < this.board.getHeight()) {
                    if (this.board.containsBomb(i, j)) counter++;
                }
            }
        }
        return counter;
    }
}
