package agent;

import level.Board;
import level.RandomBoardGenerator;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by Jonni on 3/22/2017.
 */
public class MSAgentTest {

    private PositionGrid grid;
    private RandomBoardGenerator bGen;

    @Before
    public void setUp() throws Exception {
        this.grid = new PositionGrid(24, 24);
        this.bGen = new RandomBoardGenerator();
    }

    @Test
    public void simpleGameTest() {
        Board board = new Board(15, 15);
        board.addBomb(0, 14);

        MSAgent agent = new MSAgent(15, 15,1, this.grid.getVariable(14, 0));

        boolean won = true;

        Set<Position> moves = new HashSet<>();

        Position next;
        while (moves.size() < 15 * 15 - 1) {
            next = agent.nextMove();
            if (board.containsBomb(next.getX(), next.getY())) {
                won = false;
                break;
            }
            agent.sendBackResult(next, board.adjacentBombs(next.getX(), next.getY()));
            moves.add(next);
        }

        assertTrue(won);

        assertTrue(agent.markBomb().equals(this.grid.getVariable(0, 14)));
        assertEquals(null, agent.markBomb());

        for (int i = 0; i < 15; i++) {
            for (int j = 0; j < 15; j++) {
                if (i != 0 || j != 14) assertTrue(moves.contains(this.grid.getVariable(i, j)));
            }
        }
        assertEquals(15 * 15 - 1, moves.size());
    }

    @Test
    public void preferRandomUnknownTest() {
        Board board = new Board(8,8);
        board.addBomb(3,3);
        board.addBomb(1,1);
        // 8 more bombs that I won't add but tell the agent it has

        // Probability of bombs outside the constraint is
        // (10 - 2) / (8*8 - 9) which is ~14.5%
        // while the probability for a given square around the 2 is
        // 2 / 8 = 1 / 4 which is 25%
        // Thus we should always prefer to choose random unknowns here

        /*
        ########
        #X######
        ##2#####
        ###X####
        ########
        ########
        ########
        ########
        */

        for (int i = 0; i < 50; i++) {
            MSAgent agent = new MSAgent(8, 8, 10, this.grid.getVariable(2, 2));
            agent.sendBackResult(agent.nextMove(), 2);

            Set<Position> badProbability = new HashSet<>(
                    Arrays.asList(
                            this.grid.getVariable(1, 1),
                            this.grid.getVariable(2, 1),
                            this.grid.getVariable(3, 1),
                            this.grid.getVariable(1, 2),
                            this.grid.getVariable(3, 2),
                            this.grid.getVariable(1, 3),
                            this.grid.getVariable(2, 3),
                            this.grid.getVariable(3, 3)
                    )
            );

            assertFalse(badProbability.contains(agent.nextMove()));
        }
    }

    @Test
    public void simpleGuessTest() {
        // ####
        // X21X
        // X###
        Board b = new Board(5,3);
        b.addBomb(0,2);
        b.addBomb(0,1);
        b.addBomb(3,1);

        int total = 0, bombs = 0;
        for (int i = 0; i < 1000; i++) {

            MSAgent agent = new MSAgent(5, 3, 3, new Position(1, 1));
            agent.nextMove();
            agent.sendBackResult(this.grid.getVariable(2, 1), 1);

            if (agent.nextMove().equals(this.grid.getVariable(1, 1))) {
                total++;

                agent.sendBackResult(this.grid.getVariable(1, 1), 2);

                Position next = agent.nextMove();
                assertFalse(new HashSet<>(Arrays.asList(
                        this.grid.getVariable(0, 2),
                        this.grid.getVariable(0, 2),
                        this.grid.getVariable(0, 2))).contains(next));

                if (b.containsBomb(next.getX(), next.getY())) bombs++;
            }

        }

        // Probability is about 14.3%
        assertTrue("Probability is ~14, if fails, run several times and check if its fails again, if so something is probably wrong!",
                100.0 * bombs / total < 20);
    }

    @Test
    public void noGuessGameWin() {
        /*
        0000001X
        00000011
        00000011
        0000001X
        00000022
        0000001X
        00000011
        00000000
        */
        Board b = new Board(8,8);
        b.addBomb(7,0);
        b.addBomb(7,3);
        b.addBomb(7,5);
        Random random = new Random();

        ArrayList<Position> start = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 8; j++) {
                start.add(this.grid.getVariable(i, j));
            }
        }
        start.add(this.grid.getVariable(7,7));
        start.add(this.grid.getVariable(6,6));
        start.add(this.grid.getVariable(6,7));
        start.add(this.grid.getVariable(7,6));


        for (int experiment = 0; experiment < 15; experiment++) {
            MSAgent agent = new MSAgent(8, 8, 3, start.get(random.nextInt(start.size())));

            int movesToWin = 8 * 8 - 3;
            boolean won = true;

            Position next;
            while (movesToWin-- > 0) {
                next = agent.nextMove();
                if (b.containsBomb(next.getX(), next.getY())) {
                    won = false;
                    break;
                }
                agent.sendBackResult(next, b.adjacentBombs(next.getX(), next.getY()));
            }

            assertTrue(won);

            Set<Position> expectedBombs = new HashSet<>(
                    Arrays.asList(
                            this.grid.getVariable(7, 0),
                            this.grid.getVariable(7, 3),
                            this.grid.getVariable(7, 5)
                    )
            );

            assertTrue(expectedBombs.contains(agent.markBomb()));
            assertTrue(expectedBombs.contains(agent.markBomb()));
            assertTrue(expectedBombs.contains(agent.markBomb()));
            assertEquals(null, agent.markBomb());
        }
    }
}