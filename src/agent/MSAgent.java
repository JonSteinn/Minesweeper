package agent;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSAgent {

    Random generator;
    Queue<Position> pendingMoves;
    PositionGrid grid;
    PerspectiveBoard board;
    int width;
    int height;
    int bombs;

    public MSAgent(int width, int height, int bombs) {
        init(width, height, bombs);
    }

    public void init(int width, int height, int bombs) {
        generator = new Random();
        pendingMoves = new LinkedList<>();
        board = new PerspectiveBoard(width, height);
        grid = new PositionGrid(width, height);
        firstMove();
        this.width = width;
        this.height = height;
        this.bombs = bombs;
    }

    public Position nextMove() {
        if (pendingMoves.isEmpty()) findMove();
        return pendingMoves.poll();
    }

    private void firstMove() { // TODO: is there something better to do?
        pendingMoves.add(grid.getVariable(generator.nextInt(width), generator.nextInt(height)));
    }

    private void findMove() {
        if (!search()) guess();
    }

    private boolean search() {
        // TODO
        return false;
    }

    private void guess() {
        // TODO
    }
}
