package agent;

import org.chocosolver.solver.exception.ContradictionException;

import java.util.*;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSAgent {

    private Set<Position> markedBombs;
    private Set<Position> unmarkedBombs;
    private Set<Position> history;
    private Set<Position> pendingMoves;

    private Random generator;
    private PositionGrid grid;
    private PerspectiveBoard board;

    private int width;
    private int height;
    private int bombs;
    private int nonBombs;

    public MSAgent(int width, int height, int bombs) {
        this.init(width, height, bombs);
        this.firstMove();
    }

    public MSAgent(int width, int height, int bombs, Position first) {
        this.init(width, height, bombs);
        this.pendingMoves.add(first);
    }

    public void init(int width, int height, int bombs) {
        this.markedBombs = new HashSet<>();
        this.unmarkedBombs = new HashSet<>();
        this.history = new HashSet<>();
        this.generator = new Random();
        this.pendingMoves = new HashSet<>();
        this.board = new PerspectiveBoard(width, height);
        this.grid = new PositionGrid(width, height);
        this.width = width;
        this.height = height;
        this.bombs = bombs;
        this.nonBombs = width * height - bombs;
    }

    public Position markBomb() {
        Position returnValue = null;
        while (!this.unmarkedBombs.isEmpty()) {
            Position bomb = nextBomb();
            if (!this.markedBombs.contains(bomb)) {
                this.markedBombs.add(bomb);
                returnValue = bomb;
                this.bombs--;
                break;
            }
        }
        return returnValue;
    }

    public Position nextMove() {
        Position next = null;
        while (!pendingMoves.isEmpty()) {
            Position nextMove = nextPending();
            if (!history.contains(nextMove)) {
                next = nextMove;
                history.add(nextMove);
                break;
            }
        }
        if (next == null) {
            findMove();
            next = pendingMoves.isEmpty() ? randomMove() : nextPending();
            history.add(next);
        }
        return next;
    }

    public void sendBackResult(Position position, int adjacent) {
        this.board.setAdjacent(position.getX(), position.getY(), adjacent, grid, pendingMoves, this.unmarkedBombs);
    }

    // TODO: is there something better to do?
    private void firstMove() {
        pendingMoves.add(grid.getVariable(generator.nextInt(width), generator.nextInt(height)));
    }

    private void findMove() {
        if (!search()) guess();
    }

    private boolean search() {
        boolean found = false;
        Stack<Position> bombs = new Stack<>();
        ConstraintGroups cGroups = new ConstraintGroups(this.board);
        for (Map.Entry<Set<ConstraintInfo>, Set<Position>> entry : cGroups.getGroups().entrySet()) {
            found = searchGroup(entry, bombs);
        }
        while (!bombs.isEmpty()) {
            Position position = bombs.pop();
            this.board.setBombAt(position.getX(), position.getY(), this.grid, this.pendingMoves, this.unmarkedBombs);
        }
        return (!found && !bombs.isEmpty()) ? search() : found;
    }

    private boolean searchGroup(Map.Entry<Set<ConstraintInfo>, Set<Position>> entry, Stack<Position> bombs) {
        boolean found = false;
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
            System.out.println("Something wrong with the csp model!");
        }
        return found;
    }

    private void guess() {
        Map<Position, Double> probabilities = new HashMap<>();
        ConstraintGroups cGroups = new ConstraintGroups(this.board);
    }

    private Position nextBomb() {
        Iterator<Position> it = this.unmarkedBombs.iterator();
        Position bomb = it.next();
        it.remove();
        return bomb;
    }

    private Position nextPending() {
        Iterator<Position> it = pendingMoves.iterator();
        Position pos = it.next();
        it.remove();
        return pos;
    }

    private Position randomMove() {
        ArrayList<Position> list = new ArrayList<>();
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (this.board.getBoard()[i][j] == PerspectiveBoard.UNKNOWN) list.add(this.grid.getVariable(i, j));
            }
        }
        return list.get(this.generator.nextInt(list.size()));
    }

    public ArrayList<Position> getUnknownNonVariables(Set<Position> variables) {
        ArrayList<Position> unknownNonVars = new ArrayList<>();
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                if (this.board.getBoard()[i][j] == PerspectiveBoard.UNKNOWN &&
                        !variables.contains(this.grid.getVariable(i, j))) {
                    unknownNonVars.add(this.grid.getVariable(i, j));
                }
            }
        }
        return unknownNonVars;
    }
}
