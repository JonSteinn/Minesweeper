package agent;

import level.Board;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by Jonni on 3/22/2017.
 */
public class MSAgentTest {

    private PositionGrid grid;

    @Before
    public void setUp() throws Exception {
        this.grid = new PositionGrid(24, 24);
    }

    @Test
    public void verySimpleGameTest() {
        Board board = new Board(15, 15);
        board.addBomb(0, 14);

        MSAgent agent = new MSAgent(15, 15, 1, this.grid.getVariable(14, 0));

        boolean won = true;

        Set<Position> moves = new HashSet<>();

        Position next;
        while (moves.size() < 15 * 15 - 1) {
            next = agent.nextMove();
            //System.out.println(next);
            if (board.containsBomb(next.getX(), next.getY())) {
                won = false;
                break;
            }
            agent.sendBackResult(next, board.adjacentBombs(next.getX(), next.getY()));
            moves.add(next);
        }

        assertTrue(won);

    }

}