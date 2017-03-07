package gui;

import javafx.scene.layout.GridPane;
import level.Board;

/**
 * Created by jonsteinn on 6.3.2017.
 */
public class BoardButtons extends GridPane {
    public BoardButtons(Board board) {
        for (int i = 0; i < board.getWidth(); i++) {
            for (int j = 0; j < board.getHeight(); j++) {
                this.add(new MinesweeperButton(i, j), i, j);
            }
        }
    }
}
