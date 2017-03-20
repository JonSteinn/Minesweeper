import agent.PerspectiveBoard;
import agent.PositionGrid;
import level.Board;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Jonni on 3/19/2017.
 */
public class asdf {
    @Test
    public void foo() {

        Board b = new Board(5, 5);
        b.addBomb(1, 1);
        b.addBomb(1, 0);
        b.addBomb(0, 1);
        System.out.println(b);

        PositionGrid pg = new PositionGrid(5,5);

        PerspectiveBoard pb = new PerspectiveBoard(5,5);
        pb.setAdjacent(0,0,3, pg);
        System.out.println(pb.constraintPositions);
        System.out.println(Arrays.deepToString(pb.board));

    }
}

/*
        Model m = new Model("test");
        IntVar[] vars = new IntVar[] {
                m.intVar(getString(0,0),0,1),
                m.intVar(getString(3,3),0,1),
                m.intVar(getString(4,3),0,1),
                m.intVar(getString(3,4),0,1),
                m.intVar(getString(4,4),0,1)
        };

        m.sum(new IntVar[]{ vars[0] }, "=", 1).post();
        m.sum(new IntVar[]{ vars[0] }, "=", 1).post();
        m.sum(new IntVar[]{ vars[0] }, "=", 1).post();
        m.sum(new IntVar[]{ vars[1], vars[2] }, "=", 1).post();
        m.sum(new IntVar[]{ vars[1], vars[2] }, "=", 1).post();
        m.sum(new IntVar[]{ vars[1], vars[2] }, "=", 1).post();
        m.sum(new IntVar[]{ vars[1], vars[3] }, "=", 2).post();
        m.sum(new IntVar[]{ vars[1], vars[3] }, "=", 2).post();


        Solution solution = m.getSolver().findSolution();
        if(solution != null){
            System.out.println(solution.toString());
        } else {
            System.out.println("no");
        }

*/