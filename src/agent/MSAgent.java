package agent;

import java.util.*;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSAgent {

    Random generator;
    Set<Position> pendingMoves;
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
        pendingMoves = new HashSet<>();
        board = new PerspectiveBoard(width, height);
        grid = new PositionGrid(width, height);
        firstMove();
        this.width = width;
        this.height = height;
        this.bombs = bombs;
    }

    public Position nextMove() {
        if (pendingMoves.isEmpty()) findMove();
        Iterator<Position> it = pendingMoves.iterator();
        Position pos = it.next();
        it.remove();
        return pos;
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
