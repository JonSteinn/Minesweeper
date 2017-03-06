package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import level.Board;
import level.RandomBoardGenerator;

public class Main extends Application {

    RandomBoardGenerator generator;
    BoardButtons mines;
    Button generate;

    @Override
    public void start(Stage primaryStage) throws Exception{


        generator = new RandomBoardGenerator();
        mines = new BoardButtons(generator.create(6, 6, 10, false, false));

        GridPane pane = new GridPane();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                pane.add(mines.getButton(i, j), i, j);
            }
        }
        pane.setPadding(new Insets(0,0,10,0));

        BorderPane bPane = new BorderPane();
        bPane.setCenter(pane);
        generate = new Button("Generate");
        generate.setOnMouseClicked((event) -> {
            pane.getChildren().remove(0, 36);
            mines = new BoardButtons(generator.create(6,6, 10, false, false));
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    pane.add(mines.getButton(i, j), i, j);
                }
            }
        });
        bPane.setBottom(generate);
        bPane.setPadding(new Insets(5,5,5,5));
        bPane.setAlignment(generate, Pos.CENTER);
        bPane.setAlignment(pane, Pos.CENTER);;

        BorderPane ghost = new BorderPane();
        ghost.setCenter(bPane);
        ghost.setPadding(new Insets(5,5,5,5));
        ghost.setAlignment(bPane, Pos.CENTER);

        Scene scene = new Scene(ghost);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
