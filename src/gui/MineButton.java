package gui;

import javafx.scene.control.Button;

/**
 * Created by jonsteinn on 6.3.2017.
 */
public class MineButton extends Button {
    private int adjacentMines;
    private boolean hasBomb;

    public MineButton(int adjacentMines, boolean hasBomb) {
        this.adjacentMines = adjacentMines;
        this.hasBomb = hasBomb;
        this.setMaxHeight(50);
        this.setMinHeight(50);
        this.setMaxWidth(50);
        this.setMinWidth(50);
        this.setOnMouseClicked(event -> update());
    }

    public void update() {
        if (this.hasBomb) {
            this.setText("X");
        } else {
            this.setText(Integer.toString(this.adjacentMines));
        }
    }
}
