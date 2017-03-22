package agent;

import level.Board;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by jonsteinn on 21.3.2017.
 */
public class PerspectiveBoardTest {

    private PerspectiveBoard pBoard;
    private PositionGrid grid;

    @Before
    public void setUp() {
        this.pBoard = new PerspectiveBoard(5, 5);
        this.grid = new PositionGrid(5, 5);
    }

    @Test
    public void constructorTest() {
        for (byte[] arr : this.pBoard.getBoard()) {
            for (byte b : arr) {
                assertEquals(PerspectiveBoard.UNKNOWN, b);
            }
        }
    }

    @Test
    public void clickZeroAtFirstTest() {
        Set<Position> pendingMoves = new HashSet<>();
        this.pBoard.setAdjacent(2,3,0, this.grid, pendingMoves);
        assertTrue(this.pBoard.getConstraintPositions().isEmpty());
        assertEquals(8, pendingMoves.size());
        assertEquals(0, this.pBoard.getBoard()[2][3]);
        for (Position pos : pendingMoves) {
            assertTrue(Math.sqrt((2 - pos.getX()) * (2 - pos.getX()) + (3 - pos.getY()) * (3 - pos.getY())) < 1.5);
            assertEquals(-1, this.pBoard.getBoard()[pos.getX()][pos.getY()]);
        }
    }

    @Test
    public void addBombTest() {
        Set<Position> pendingMoves = new HashSet<>();

        System.out.println("Step 1");
        /*
        #1###
        #####
        #####
        #####
        #####
         */
        this.pBoard.setAdjacent(1,0, 1, this.grid, pendingMoves);
        Set<Position> expectedVariables1 = new HashSet<>();
        expectedVariables1.addAll(Arrays.asList(
                this.grid.getVariable(0,0),
                this.grid.getVariable(0,1),
                this.grid.getVariable(1,1),
                this.grid.getVariable(2,0),
                this.grid.getVariable(2,1)
        ));
        assertEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());
        assertTrue(pendingMoves.isEmpty());

        System.out.println("Step 2");
        /*
        #1###
        1####
        #####
        #####
        #####
         */
        this.pBoard.setAdjacent(0,1, 1, this.grid, pendingMoves);
        Set<Position> expectedVariables2 = new HashSet<>();
        expectedVariables2.addAll(Arrays.asList(
                this.grid.getVariable(0,0),
                this.grid.getVariable(1,1),
                this.grid.getVariable(0,2),
                this.grid.getVariable(1,2)
        ));
        assertEquals(expectedVariables2, this.pBoard.getConstraintPositions().get(this.grid.getVariable(0,1)).getUnknownNeighbours());
        assertNotEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());
        expectedVariables1.remove(this.grid.getVariable(0, 1));
        assertEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());
        assertTrue(pendingMoves.isEmpty());

        System.out.println("Step 3");
        /*
        #1###
        11###
        #####
        #####
        #####
         */
        this.pBoard.setAdjacent(1,1, 1, this.grid, pendingMoves);
        Set<Position> expectedVariables3 = new HashSet<>();
        expectedVariables3.addAll(Arrays.asList(
                this.grid.getVariable(2,0),
                this.grid.getVariable(0,2),
                this.grid.getVariable(2,1),
                this.grid.getVariable(1,2),
                this.grid.getVariable(2,2),
                this.grid.getVariable(0,0)
        ));
        assertEquals(expectedVariables3, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,1)).getUnknownNeighbours());
        assertNotEquals(expectedVariables2, this.pBoard.getConstraintPositions().get(this.grid.getVariable(0,1)).getUnknownNeighbours());
        assertNotEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());
        expectedVariables1.remove(this.grid.getVariable(1,1));
        expectedVariables2.remove(this.grid.getVariable(1,1));
        assertEquals(expectedVariables2, this.pBoard.getConstraintPositions().get(this.grid.getVariable(0,1)).getUnknownNeighbours());
        assertEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());
        assertTrue(pendingMoves.isEmpty());

        System.out.println("Step 4");
        /*
        #10##
        11###
        #####
        #####
        #####
         */
        this.pBoard.setAdjacent(2,0, 0, this.grid, pendingMoves);
        assertFalse(pendingMoves.isEmpty());
        assertEquals(3, pendingMoves.size());
        assertTrue(pendingMoves.containsAll(Arrays.asList(this.grid.getVariable(3,0), this.grid.getVariable(3,1), this.grid.getVariable(2,1))));
        assertFalse(this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours().contains(this.grid.getVariable(2,0)));

        assertNotEquals(expectedVariables3, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,1)).getUnknownNeighbours());
        assertNotEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());
        expectedVariables1.remove(this.grid.getVariable(2, 0));
        expectedVariables3.remove(this.grid.getVariable(2, 0));
        assertEquals(expectedVariables3, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,1)).getUnknownNeighbours());
        assertEquals(expectedVariables2, this.pBoard.getConstraintPositions().get(this.grid.getVariable(0,1)).getUnknownNeighbours());
        assertEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());

        System.out.println("Step 5");
        /*
        #10##
        110##
        #####
        #####
        #####
         */
        this.pBoard.setAdjacent(2, 1, 0, this.grid, pendingMoves);
        assertEquals(7, pendingMoves.size());
        assertTrue(pendingMoves.containsAll(Arrays.asList(
                this.grid.getVariable(3,0),
                this.grid.getVariable(3,1),
                this.grid.getVariable(3,2),
                this.grid.getVariable(2,2),
                this.grid.getVariable(1,2),
                this.grid.getVariable(0,2)
        )));
        assertEquals(10, this.pBoard.getBoard()[0][0]);
        assertEquals(1, this.pBoard.getBoard()[1][0]);
        assertEquals(1, this.pBoard.getBoard()[0][1]);
        assertEquals(1, this.pBoard.getBoard()[1][1]);
        assertEquals(0, this.pBoard.getBoard()[2][0]);
        assertEquals(0, this.pBoard.getBoard()[2][1]);
        pendingMoves.remove(this.grid.getVariable(2,1));
        for (Position p : pendingMoves) assertEquals(-1, this.pBoard.getBoard()[p.getX()][p.getY()]);

    }

    @Test
    public void crowdedTest() {
        /*
        #####
        #####
        ##8##
        #####
        #####
         */
        Set<Position> pendingMoves = new HashSet<>();
        this.pBoard.setAdjacent(2,2, 8, this.grid, pendingMoves);
        assertTrue(pendingMoves.isEmpty());
        assertTrue(this.pBoard.getConstraintPositions().isEmpty());

        Set<Position> bombs = new HashSet<>();
        bombs.addAll(Arrays.asList(
                this.grid.getVariable(1,1),
                this.grid.getVariable(1,2),
                this.grid.getVariable(1,3),
                this.grid.getVariable(2,1),
                this.grid.getVariable(2,3),
                this.grid.getVariable(3,1),
                this.grid.getVariable(3,2),
                this.grid.getVariable(3,3)
        ));
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (bombs.contains(this.grid.getVariable(i, j))) {
                    assertEquals(10, this.pBoard.getBoard()[i][j]);
                } else if (i == 2 && j == 2) {
                    assertEquals(8, this.pBoard.getBoard()[i][j]);
                } else {
                    assertEquals(-1, this.pBoard.getBoard()[i][j]);
                }
            }
        }
    }

    @Test
    public void nextToBombTest() {
        Set<Position> pendingMoves = new HashSet<>();
        this.pBoard.setBombAt(1,1, this.grid, pendingMoves);
        this.pBoard.setBombAt(3,3, this.grid, pendingMoves);
        assertTrue(pendingMoves.isEmpty());
        assertTrue(this.pBoard.getConstraintPositions().isEmpty());

        this.pBoard.setAdjacent(2,2,3, this.grid, pendingMoves);
        assertEquals(1, this.pBoard.getConstraintPositions().size());
        assertTrue(pendingMoves.isEmpty());
        assertFalse(this.pBoard
                        .getConstraintPositions()
                        .get(this.grid.getVariable(2,2))
                        .getUnknownNeighbours()
                        .contains(this.grid.getVariable(1,1))
        );
        assertEquals(1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(2,2)).getAdjacentBombs());
        System.out.println(this.pBoard.getConstraintPositions().get(this.grid.getVariable(2,2)).getUnknownNeighbours());
        assertTrue(this.pBoard.getConstraintPositions()
                .get(this.grid.getVariable(2,2))
                .getUnknownNeighbours()
                .containsAll(Arrays.asList(
                        this.grid.getVariable(2,3),
                        this.grid.getVariable(3,2),
                        this.grid.getVariable(1,2),
                        this.grid.getVariable(2,1),
                        this.grid.getVariable(1,3),
                        this.grid.getVariable(3,1)
                ))
        );
    }

    @Test
    public void gameTest1() {
        Set<Position> pending = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            this.pBoard.setAdjacent(i,2, 0, this.grid, pending);
            if (i != 2) this.pBoard.setAdjacent(2,i, 0, this.grid, pending);
        }
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if ((i == 0 && j == 0) || (i == 4 && j == 4)) assertFalse(pending.contains(this.grid.getVariable(i,j)));
            }
        }
        this.pBoard.setAdjacent(1,1,1,this.grid,pending);
        this.pBoard.setAdjacent(0,1,1,this.grid,pending);
        this.pBoard.setAdjacent(1,0,1,this.grid,pending);
        this.pBoard.setAdjacent(4,3,1,this.grid,pending);
        this.pBoard.setAdjacent(3,3,1,this.grid,pending);
        this.pBoard.setAdjacent(3,4,1,this.grid,pending);
        assertTrue(this.pBoard.getConstraintPositions().isEmpty());
        assertEquals(10, this.pBoard.getBoard()[0][0]);
        assertEquals(10, this.pBoard.getBoard()[4][4]);

        /*
        x1###
        11###
        #####
        ###11
        ###1x
         */
    }

    @Test
    public void foo() {
        /*
        ........
        .1221...
        .1XX2232
        .1222XXX
        11..13X4
        X1...13X
        221...2X
        1X1...11

        (4,0)
        (5,0)
        (6,0)
        (7,0)
        (7,1)
        (6,1)
        (7,2)
        (5,1)
        (6,2)
         */
        Board board = new Board(8, 8);
        board.addBomb(2,2);
        board.addBomb(3,2);
        board.addBomb(5,3);
        board.addBomb(6,3);
        board.addBomb(7,3);
        board.addBomb(6,4);
        board.addBomb(0,5);
        board.addBomb(7,5);
        board.addBomb(7,6);
        board.addBomb(1,7);

        Set<Position> pending = new HashSet<>();
        PositionGrid grid = new PositionGrid(8, 8);

        PerspectiveBoard pBoard = new PerspectiveBoard(8, 8);
        String[] tmp = new String[]{"(4,0)", "(5,0)", "(6,0)", "(7,0)", "(7,1)", "(6,1)", "(7,2)", "(5,1)", "(6,2)"};
        Queue<Position> q = new LinkedList<>();
        for (String t : tmp) q.add(Position.fromString(t));
        while (q.size() > 1) {
            Position next = q.poll();
            pBoard.setAdjacent(next.getX(), next.getY(), board.adjacentBombs(next.getX(), next.getY()), grid, pending);
        }


        pBoard.setAdjacent(6,2,3,grid,pending);

        System.out.println(pending);
        p(pBoard.getBoard());
    }

    void p(byte[][] b) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                System.out.print(b[j][i]+ " ");
            }
            System.out.println();
        }
    }
}