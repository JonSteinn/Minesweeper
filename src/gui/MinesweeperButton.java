package gui;

import javafx.scene.control.Button;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by jonsteinn on 7.3.2017.
 */
public class MinesweeperButton extends Button {
    private int x;
    private int y;
    private boolean clicked;
    public MinesweeperButton(int x, int y) {
        this.x = x;
        this.y = y;
        this.clicked = false;
        this.setOnAction(event -> Controller.controller.buttonUpdate(this, this.x, this.y));
        this.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY && !clicked) {
                setText(getText().equals("#") ? "" : "#");
            }
        });
        this.setMaxWidth(30);
        this.setMinWidth(30);
        this.setMaxHeight(30);
        this.setMinHeight(30);
    }
    public boolean isDown() {
        return this.clicked;
    }
    public void click() {
        this.clicked = true;
    }
}
