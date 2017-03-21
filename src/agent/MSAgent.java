package agent;

import org.chocosolver.solver.exception.ContradictionException;

import java.util.*;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSAgent {

    public Random generator;
    public Set<Position> pendingMoves;
    public PositionGrid grid;
    public PerspectiveBoard board;
    public int width;
    public int height;
    public int bombs;

    public MSAgent(int width, int height, int bombs) {
        init(width, height, bombs);
    }

    public void init(int width, int height, int bombs) {
        generator = new Random();
        pendingMoves = new HashSet<>();
        board = new PerspectiveBoard(width, height);
        grid = new PositionGrid(width, height);
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        firstMove();
    }

    public Position nextMove() {
        if (pendingMoves.isEmpty()) findMove();
        Iterator<Position> it = pendingMoves.iterator();
        Position pos = it.next();
        it.remove();
        return pos;
    }

    public void sendBackResult(Position position, int adjacent) {
        this.board.setAdjacent(position.getX(), position.getY(), adjacent, grid, pendingMoves);
    }

    private void firstMove() { // TODO: is there something better to do?
        pendingMoves.add(grid.getVariable(generator.nextInt(width), generator.nextInt(height)));
    }

    private void findMove() {
        if (!search()) guess();
    }

    private boolean search() {
        boolean found = false;
        Stack<Position> bombs = new Stack<>();
        ConstraintGroups cGroups = new ConstraintGroups(this.board);
        for (Map.Entry<Set<ConstraintInfo>, Set<Position>> entry : cGroups.groups.entrySet()) {
            try {
                MSModel model = new MSModel(entry.getKey(), entry.getValue());
                for (Position position : entry.getValue()) {
                    if (model.hasNoBombs(position)) {
                        pendingMoves.add(position);
                        found = true;
                    }
                    else if (model.hasBomb(position)) bombs.add(position);
                }
            } catch (ContradictionException e) {
                System.out.println("Something wrong with model --- debug!");
            }
        }
        boolean searchAgain = (!found && !bombs.isEmpty());
        while (!bombs.isEmpty()) {
            Position position = bombs.pop();
            this.board.setBombAt(position.getX(), position.getY(), this.grid);
        }
        return searchAgain ? search() : found;
    }

    private void guess() {
        // TODO
    }
}
