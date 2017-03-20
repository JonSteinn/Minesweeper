import agent.PerspectiveBoard;
import agent.Position;
import agent.PositionGrid;
import level.Board;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Jonni on 3/19/2017.
 */
public class asdf {
    @Test
    public void foo() {

        int h = 55, w = 55;
        Board b = new Board(w, h);
        System.out.println(b);
        PositionGrid pg = new PositionGrid(w,h);
        PerspectiveBoard pb = new PerspectiveBoard(w,h);
        Set<Position> q = new HashSet<>();
        pb.setAdjacent(2,2,0,pg, q);

        while (!q.isEmpty()) {
            Iterator<Position> it = q.iterator();
            Position next = it.next();
            it.remove();
            pb.setAdjacent(next.getX(), next.getY(), 0, pg, q);
        }

        System.out.println(Arrays.deepToString(pb.board));

    }

    @Test
    public void asdf() {
        Model m = new Model("tsadfest");
        IntVar[] vars = new IntVar[] {
                m.intVar("a",0,1),
                m.intVar("b",0,1),
                m.intVar("c",0,1),
        };

        m.sum(new IntVar[]{ vars[0], vars[1] }, "=", 1).post();
        m.sum(new IntVar[]{ vars[1], vars[2], vars[0] }, "=", 2).post();
        m.sum(new IntVar[]{ vars[1], vars[2] }, "=", 1).post();

        try {
            m.getSolver().propagate();
        } catch (ContradictionException e) {
            System.out.println("A");
        }

        Constraint c = m.arithm(vars[0], "=", 0);

        m.getEnvironment().worldPush();
        m.post(c);

        try {
            m.getSolver().propagate();
        } catch (ContradictionException e) {
            System.out.println("B");
        }

        m.getEnvironment().worldPop();
        m.unpost(c);


        Constraint cc = m.arithm(vars[0], "=", 1);
        cc.post();
        try {
            m.getSolver().propagate();
        } catch (ContradictionException e) {
            System.out.println("C");
        }


    }
}