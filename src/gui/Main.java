package gui;

import javafx.application.Application;
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
    Board board;
    Button[][] buttons;
    Button generato;

    @Override
    public void start(Stage primaryStage) throws Exception{


        generator = new RandomBoardGenerator();
        board = generator.create(10, 10, 30, false, false);

        GridPane pane = new GridPane();
        buttons = new Button[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                buttons[i][j] = new Button(board.containsBomb(i,j) ? "X" : "O");
                pane.add(buttons[i][j], i, j);
            }
        }

        BorderPane bPane = new BorderPane();
        bPane.setCenter(pane);
        generato = new Button("Generate");
        generato.setOnMouseClicked((event) -> {
            board = generator.create(10,10,30, false, false);
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    buttons[i][j].setText(board.containsBomb(i,j) ? "X" : "O");
                }
            }
        });
        bPane.setBottom(generato);

        Scene scene = new Scene(bPane,550 ,450, Color.LIGHTGRAY);

        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
