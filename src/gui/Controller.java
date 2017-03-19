package gui;

import javafx.application.Platform;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import level.Board;
import level.RandomBoardGenerator;

public class Controller {

    public static Controller controller = new Controller();

    private Player player = Player.HUMAN;
    private Difficulty difficulty = Difficulty.EASY;
    private Board board;
    private GameState state = GameState.NOT_STARTED;
    private RandomBoardGenerator boardGenerator;
    private BoardButtons boardButtons;
    private BorderPane root;
    private MinesweeperMenu menu;
    private Footer footer;

    private Stage stage;

    private Controller() {
        this.player = Player.HUMAN;
        this.difficulty = Difficulty.EASY;
        this.state = GameState.NOT_STARTED;
        this.boardGenerator = new RandomBoardGenerator();
        this.board = null;
        this.boardButtons = null;
        this.root = new BorderPane();
        this.menu = new MinesweeperMenu();
        this.footer = new Footer();
        this.root.setTop(this.menu);

        this.board = this.boardGenerator.create(8, 8, 10, false, false);
        this.boardButtons = new BoardButtons(this.board);
        this.root.setCenter(this.boardButtons);

        this.root.setBottom(this.footer);

    }

    public void exit() {
        Platform.exit();
    }

    public void newGame() {
        switch (difficulty) {
            case EASY:
                this.board = this.boardGenerator.create(8, 8, 10, false, false);
                break;
            case MEDIUM:
                this.board = this.boardGenerator.create(16, 16, 40, false, false);
                break;
            case HARD:
                this.board = this.boardGenerator.create(24, 24, 99, false, false);
        }
        this.boardButtons = new BoardButtons(this.board);
        this.root.setCenter(this.boardButtons);
        this.resizeStage();
    }

    public void setPlayer(Player player) {
        this.player = player;
        newGame();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        newGame();
    }

    public BorderPane getRoot() {
        return this.root;
    }

    public void buttonUpdate(MinesweeperButton button, int x, int y) {
        if (button.isDown()) return;
        button.click();

        if (this.board.containsBomb(x,y)) {
            button.setText("X");
            button.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
        } else {
            int adj = this.board.adjacentBombs(x, y);
            if (adj == 0) {
                if (!board.outOfBounds(x - 1, y - 1)) this.boardButtons.get(x - 1, y - 1).fire();
                if (!board.outOfBounds(x, y - 1)) this.boardButtons.get(x, y - 1).fire();
                if (!board.outOfBounds(x + 1, y - 1)) this.boardButtons.get(x + 1, y - 1).fire();
                if (!board.outOfBounds(x - 1, y)) this.boardButtons.get(x - 1, y).fire();
                if (!board.outOfBounds(x + 1, y)) this.boardButtons.get(x + 1, y).fire();
                if (!board.outOfBounds(x - 1, y + 1)) this.boardButtons.get(x - 1, y + 1).fire();
                if (!board.outOfBounds(x, y + 1)) this.boardButtons.get(x, y + 1).fire();
                if (!board.outOfBounds(x + 1, y + 1)) this.boardButtons.get(x + 1, y + 1).fire();
            } else {
                button.setText(Integer.toString(adj));
            }
            button.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, null, null)));
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void resizeStage() {
        this.stage.sizeToScene();
    }

}
