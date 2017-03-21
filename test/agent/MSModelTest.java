package agent;

import org.chocosolver.solver.exception.ContradictionException;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * Created by Jonni on 3/21/2017.
 */
public class MSModelTest {

    private PositionGrid grid;

    @Before
    public void setUp() {
        this.grid = new PositionGrid(24, 24);
    }

    @Test
    public void solveTest() throws ContradictionException {
        //###
        //232
        Set<ConstraintInfo> constraints = new HashSet<>();
        Set<Position> s1 = new HashSet<>(), s2 = new HashSet<>(), s3 = new HashSet<>(), s = new HashSet<>();
        s.addAll(Arrays.asList(this.grid.getVariable(0,0), this.grid.getVariable(1,0), this.grid.getVariable(2,0)));
        s1.addAll(Arrays.asList(this.grid.getVariable(0,0), this.grid.getVariable(1,0)));
        s2.addAll(Arrays.asList(this.grid.getVariable(0,0), this.grid.getVariable(1,0), this.grid.getVariable(2,0)));
        s3.addAll(Arrays.asList(this.grid.getVariable(1,0), this.grid.getVariable(2,0)));
        constraints.addAll(Arrays.asList(
                new ConstraintInfo(s1, 2),
                new ConstraintInfo(s2, 3),
                new ConstraintInfo(s3, 2)
        ));
        MSModel model = new MSModel(constraints, s);
        for (Position p : s) {
            assertTrue(model.hasBomb(p));
            assertFalse(model.hasNoBombs(p));
        }
    }

    @Test
    public void solveTest2() throws ContradictionException {
        //###
        //12
        Set<ConstraintInfo> constraints = new HashSet<>();
        Set<Position> s1 = new HashSet<>(), s2 = new HashSet<>(), s = new HashSet<>();
        s.addAll(Arrays.asList(this.grid.getVariable(0,0), this.grid.getVariable(1,0), this.grid.getVariable(2,0)));
        s1.addAll(Arrays.asList(this.grid.getVariable(0,0), this.grid.getVariable(1,0)));
        s2.addAll(Arrays.asList(this.grid.getVariable(0,0), this.grid.getVariable(1,0), this.grid.getVariable(2,0)));
        constraints.addAll(Arrays.asList(
                new ConstraintInfo(s1, 1),
                new ConstraintInfo(s2, 2)
        ));
        MSModel model = new MSModel(constraints, s);
        assertTrue(model.hasBomb(this.grid.getVariable(2,0)));
        assertFalse(model.hasNoBombs(this.grid.getVariable(2,0)));
        assertFalse(model.hasNoBombs(this.grid.getVariable(1,0)));
        assertFalse(model.hasNoBombs(this.grid.getVariable(0,0)));
        assertFalse(model.hasBomb(this.grid.getVariable(1,0)));
        assertFalse(model.hasBomb(this.grid.getVariable(0,0)));
    }

    @Test
    public void solveTest3() throws ContradictionException {
        // ###
        // #31
        // #1
        Set<ConstraintInfo> constraints = new HashSet<>();
        Set<Position> s1 = new HashSet<>(), s2 = new HashSet<>(), s3 = new HashSet<>(), s = new HashSet<>();
        s.addAll(Arrays.asList(
                this.grid.getVariable(0,0),
                this.grid.getVariable(1,0),
                this.grid.getVariable(2,0),
                this.grid.getVariable(0,1),
                this.grid.getVariable(0,2)
        ));
        s1.addAll(Arrays.asList(this.grid.getVariable(0,1), this.grid.getVariable(0,2)));
        s2.addAll(Arrays.asList(
                this.grid.getVariable(0,0),
                this.grid.getVariable(1,0),
                this.grid.getVariable(2,0),
                this.grid.getVariable(0,1),
                this.grid.getVariable(0,2)
        ));
        s3.addAll(Arrays.asList(this.grid.getVariable(1,0), this.grid.getVariable(2,0)));
        constraints.addAll(Arrays.asList(
                new ConstraintInfo(s1, 1),
                new ConstraintInfo(s2, 3),
                new ConstraintInfo(s3, 1)
        ));
        MSModel model = new MSModel(constraints, s);
        assertTrue(model.hasBomb(this.grid.getVariable(0,0)));
        for (Position pos : s) {
            assertFalse(model.hasNoBombs(pos));
            if (!pos.equals(this.grid.getVariable(0,0))) assertFalse(model.hasBomb(pos));
        }
    }

    @Test
    public void solveTest4() throws ContradictionException {
        // ###############
        // 121212121212121
        // 012345678901234 (14)

        Set<ConstraintInfo> constraints = new HashSet<>();
        Set<Position> vars = new HashSet<>();
        for (int i = 0; i < 14; i++) vars.add(this.grid.getVariable(i, 0));

        Set<Position> temp = new HashSet<>();
        temp.add(new Position(0,0)); temp.add(new Position(1,0));
        constraints.add(new ConstraintInfo(temp, 1));

        for (int i = 1; i < 13; i++) {
            temp = new HashSet<>();
            temp.add(new Position(i-1, 0));
            temp.add(new Position(i,0));
            temp.add(new Position(i+1,0));
            constraints.add(new ConstraintInfo(temp, (i & 1) == 1 ? 2 : 1));
        }
        temp = new HashSet<>();
        temp.add(new Position(12,0)); temp.add(new Position(13,0));
        constraints.add(new ConstraintInfo(temp, 1));

        MSModel model = new MSModel(constraints, vars);

        for (int i = 0; i < 14; i++) {
            if ((i & 1) == 1) {
                assertFalse(model.hasBomb(this.grid.getVariable(i ,0)));
                assertTrue(model.hasNoBombs(this.grid.getVariable(i, 0)));
            } else {
                assertTrue(model.hasBomb(this.grid.getVariable(i, 0)));
                assertFalse(model.hasNoBombs(this.grid.getVariable(i, 0)));
            }
        }
    }
}