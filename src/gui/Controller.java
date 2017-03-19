package gui;

import agent.RandomAgent;
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
    private GameState state = GameState.IDLE;
    private RandomBoardGenerator boardGenerator;
    private BoardButtons boardButtons;
    private BorderPane root;
    private MinesweeperMenu menu;
    private Footer footer;
    private Stage stage;
    private int clicksToWin;
    private RandomAgent agent;

    private Controller() {
        this.player = Player.HUMAN;
        this.difficulty = Difficulty.EASY;
        this.state = GameState.IDLE;
        this.boardGenerator = new RandomBoardGenerator();
        this.board = null;
        this.boardButtons = null;
        this.root = new BorderPane();
        this.menu = new MinesweeperMenu();
        this.footer = new Footer();
        this.root.setTop(this.menu);
        this.board = this.boardGenerator.create(8, 8, 10, false, false);
        this.clicksToWin = 8 * 8 - 10;
        this.boardButtons = new BoardButtons(this.board);
        this.root.setCenter(this.boardButtons);
        this.root.setBottom(this.footer);
        this.footer.getPlay().setDisable(true);
        this.agent = null;
    }

    public void exit() {
        Platform.exit();
    }

    public void newGame() {
        this.state = GameState.IDLE;
        switch (difficulty) {
            case EASY:
                this.board = this.boardGenerator.create(8, 8, 10, false, false);
                this.footer.getBombsLeft().setAmountLeft(10);
                this.clicksToWin = 8 * 8 - 10;
                break;
            case MEDIUM:
                this.board = this.boardGenerator.create(16, 16, 40, false, false);
                this.footer.getBombsLeft().setAmountLeft(40);
                this.clicksToWin = 16 * 16 - 40;
                break;
            case HARD:
                this.board = this.boardGenerator.create(24, 24, 99, false, false);
                this.footer.getBombsLeft().setAmountLeft(99);
                this.clicksToWin = 24 * 24 - 99;
        }
        this.footer.getTimer().restartPlayClock();
        this.footer.setStatus(this.state);
        if (this.player == Player.HUMAN) footer.getPlay().setDisable(true);
        else footer.getPlay().setDisable(false);
        this.boardButtons = new BoardButtons(this.board);
        this.root.setCenter(this.boardButtons);
        this.resizeStage();
    }

    public void setPlayer(Player player) {
        this.player = player;
        newGame();
        if (player == Player.Computer) this.agent = new RandomAgent();
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        newGame();
    }

    public BorderPane getRoot() {
        return this.root;
    }

    public void buttonUpdate(MinesweeperButton button, int x, int y) {
        if (this.state == GameState.LOST || this.state == GameState.WON || button.isDown() || button.getText().equals("#")) return;
        if (this.state == GameState.IDLE) {
            this.state = GameState.PLAYING;
            this.footer.setStatus(this.state);
            this.footer.getTimer().startPlayClock();
        }
        button.click();

        if (this.board.containsBomb(x,y)) {
            button.setText("X");
            button.setBackground(new Background(new BackgroundFill(Color.RED, null, null)));
            this.state = GameState.LOST;
            this.footer.setStatus(this.state);
            this.footer.getTimer().stopPlayClock();
        } else {
            this.clicksToWin--;
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
            if (clicksToWin == 0) {
                this.state = GameState.WON;
                this.footer.setStatus(this.state);
                this.footer.getTimer().stopPlayClock();
            }
        }
    }

    public void playAsComputer() {
        while (this.state != GameState.LOST && this.state != GameState.WON) {
            int[] next = this.agent.nextMove(this.board.getWidth(), this.board.getHeight());
            this.boardButtons.get(next[0], next[1]).fire();
        }
    }

    public void markBomb(MinesweeperButton button) {
        if (this.state == GameState.LOST || this.state == GameState.WON) return;
        if (button.getText().equals("#")) {
            button.setText("");
            this.footer.getBombsLeft().incrementBombsLeft();
        } else {
            button.setText("#");
            this.footer.getBombsLeft().decrementBombsLeft();
        }
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void resizeStage() {
        this.stage.sizeToScene();
    }

}
