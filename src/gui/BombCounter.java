package gui;

import javafx.scene.control.Label;

/**
 * Created by Jonni on 3/19/2017.
 */
public class BombCounter extends Label {
    private int amountLeft;

    public BombCounter(int amountLeft) {
        this.setAmountLeft(amountLeft);
    }

    public void setAmountLeft(int amountLeft) {
        this.amountLeft = amountLeft;
        this.setBombText();
    }

    public void incrementBombsLeft() {
        this.amountLeft++;
        this.setBombText();
    }

    public void decrementBombsLeft() {
        this.amountLeft--;
        this.setBombText();
    }

    private void setBombText() {
        // TODO: generalize for more than 99
        if (this.amountLeft > 10) {
            this.setText(String.format(" %d", this.amountLeft));
        } else if (this.amountLeft >= 0) {
            this.setText(String.format("  %d", this.amountLeft));
        } else if (this.amountLeft > -10) {
            this.setText(String.format(" %d", this.amountLeft));
        } else {
            this.setText(String.format("%d", this.amountLeft));
        }
    }
}
