package gui;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by Jonni on 3/19/2017.
 */
public class Footer extends VBox {

    private Timer timer;
    private BombCounter bombsLeft;
    private Button play;
    private Label status;

    public Footer() {
        this.timer = new Timer();
        this.bombsLeft = new BombCounter(10);
        this.play = new Button("Start");
        this.status = new Label("Idle");

        HBox upper = new HBox();
        upper.setSpacing(25);
        upper.setAlignment(Pos.CENTER);
        upper.getChildren().addAll(this.timer, this.bombsLeft, this.play);

        HBox lower = new HBox();
        lower.setAlignment(Pos.CENTER);
        lower.getChildren().addAll(this.status);

        this.setSpacing(5);
        this.getChildren().addAll(upper, lower);
    }

    public Timer getTimer() {
        return this.timer;
    }

    public BombCounter getBombsLeft() {
        return this.bombsLeft;
    }

    public Button getPlay() {
        return this.play;
    }

    public void setStatus(GameState state) {
        this.status.setText(state.toString());
    }
}
