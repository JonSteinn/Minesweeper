package agent;

import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        /*
        #10##
        110##
        #####
        #####
        #####
         */

        Set<Position> pendingMoves = new HashSet<>();
        this.pBoard.setAdjacent(1,0, 1, this.grid, pendingMoves);

        System.out.println("Step 1");
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
        this.pBoard.setAdjacent(2,0, 0, this.grid, pendingMoves);
        assertFalse(pendingMoves.isEmpty());
        assertEquals(3, pendingMoves.size());
        assertTrue(pendingMoves.containsAll(Arrays.asList(this.grid.getVariable(3,0), this.grid.getVariable(3,1), this.grid.getVariable(2,1))));
        assertFalse(this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours().contains(this.grid.getVariable(2,0)));
        assertNotEquals(expectedVariables3, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,1)).getUnknownNeighbours());
        assertNotEquals(expectedVariables2, this.pBoard.getConstraintPositions().get(this.grid.getVariable(0,1)).getUnknownNeighbours());
        assertNotEquals(expectedVariables1, this.pBoard.getConstraintPositions().get(this.grid.getVariable(1,0)).getUnknownNeighbours());
        expectedVariables1.remove(this.grid.getVariable(2, 0));
        expectedVariables2.remove(this.grid.getVariable(2, 0));
        expectedVariables3.remove(this.grid.getVariable(2, 0));


        System.out.println(expectedVariables1);
        System.out.println(expectedVariables2);
        System.out.println(expectedVariables3);


        System.out.println("Step 5");
        this.pBoard.setAdjacent(2, 1, 0, this.grid, pendingMoves);



        System.out.println(this.pBoard.getConstraintPositions());
        print(this.pBoard.getBoard());

    }

    void print(byte[][] b) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(b[j][i] + " ");
            }
            System.out.println();
        }
    }
}