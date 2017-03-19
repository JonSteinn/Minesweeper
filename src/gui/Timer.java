package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Created by Jonni on 3/19/2017.
 */
public class Timer extends Label {
    private int seconds;
    private int minutes;
    private Timeline timeEvent;

    public Timer() {
        this.setText("00:00");
        this.timeEvent = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            this.addSecond();
            this.setText(timeString());
        }));
        this.timeEvent.setCycleCount(Timeline.INDEFINITE);
    }

    private void addSecond() {
        this.seconds++;
        if (this.seconds == 60) {
            this.seconds = 0;
            this.minutes++;
        }
        // TODO: fails if > hour
    }

    private String timeString() {
        if (seconds < 10) {
            if (minutes < 10) {
                return String.format("0%d:0%d", minutes, seconds);
            } else {
                return String.format("%d:0%d", minutes, seconds);
            }
        } else {
            if (minutes < 10) {
                return String.format("0%d:%d", minutes, seconds);
            } else {
                return String.format("%d:%d", minutes, seconds);
            }
        }
    }

    public void restartPlayClock() {
        this.seconds = 0;
        this.minutes = 0;
        this.setText(timeString());
    }

    public void startPlayClock() {
        this.timeEvent.playFromStart();
    }

    public void stopPlayClock() {
        this.timeEvent.stop();
    }


}
