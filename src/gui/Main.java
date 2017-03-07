package gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane root = new BorderPane();


        MenuBar menuBar = new MenuBar();
        Menu file = new Menu("File");
        MenuItem newGame = new MenuItem("New");
        MenuItem preferences = new MenuItem("Preferences");
        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(event -> Platform.exit());
        file.getItems().addAll(newGame, preferences, new SeparatorMenuItem(),exit);

        menuBar.getMenus().add(file);


        root.setTop(menuBar);

        BorderPane canvas = new BorderPane();
        canvas.setCenter(new TextArea("ffasdfsdfasdf"));

        Scene scene = new Scene(root);
        scene.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.ESCAPE) Platform.exit();
        });

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
