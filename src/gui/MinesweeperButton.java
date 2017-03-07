package gui;

import javafx.scene.control.Button;

/**
 * Created by jonsteinn on 7.3.2017.
 */
public class MinesweeperButton extends Button {
    private int x;
    private int y;
    public MinesweeperButton(int x, int y) {
        this.x = x;
        this.y = y;
        this.setOnAction(event -> Controller.controller.buttonUpdate(this, this.x, this.y));
        this.setMaxWidth(25);
        this.setMinWidth(25);
        this.setMaxHeight(25);
        this.setMinHeight(25);
    }
}
