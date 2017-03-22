package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

/**
 * Programs starting point.
 */
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Scene scene = new Scene(Controller.controller.getRoot());
        scene.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ESCAPE) Controller.controller.exit();
            if (event.getCode() == KeyCode.F1) Controller.controller.newGame(); // TODO: remove and create a better system
        });
        Controller.controller.setStage(primaryStage);
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
