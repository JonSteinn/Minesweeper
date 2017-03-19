package gui;

import javafx.scene.layout.GridPane;
import level.Board;

/**
 * Created by jonsteinn on 6.3.2017.
 */
public class BoardButtons extends GridPane {
    MinesweeperButton[][] access;
    public BoardButtons(Board board) {
        this.access = new MinesweeperButton[board.getWidth()][board.getHeight()];
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                MinesweeperButton button = new MinesweeperButton(i,j);
                this.add(button, i, j);
                this.access[i][j] = button;
            }
        }
    }
    public MinesweeperButton get(int x, int y) {
        return this.access[x][y];
    }
}
