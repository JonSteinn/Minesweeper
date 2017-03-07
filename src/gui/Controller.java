package gui;

import javafx.application.Platform;
import javafx.scene.layout.BorderPane;
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
        this.root.setTop(this.menu);
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
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public BorderPane getRoot() {
        return this.root;
    }

    public void buttonUpdate(MinesweeperButton button, int x, int y) {

        button.setText(this.board.containsBomb(x,y) ? "X" : Integer.toString(this.board.adjacentBombs(x, y)));
    }

    public void setState(Stage stage) {
        this.stage = stage;
    }
    public void resizeStage() {
        this.stage.sizeToScene();
    }

}
