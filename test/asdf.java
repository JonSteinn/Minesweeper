import csp.MSConstraint;
import csp.MSVariable;
import level.Board;
import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.variables.IntVar;
import org.junit.Test;

/**
 * Created by Jonni on 3/19/2017.
 */
public class asdf {
    @Test
    public void foo() {

        System.out.println(MSVariable.fromString("(123,532)"));
        System.out.println(new MSConstraint(5, new MSVariable(1,1), new MSVariable(2,3)));


        if (true) return;
        Board b = new Board(5,5);
        b.addBomb(0,0);
        b.addBomb(3,3);
        b.addBomb(3,4);
        System.out.println(b);

        Model m = new Model("test");

        int[][] myBoard = new int[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                myBoard[i][j] = -1;
            }
        }

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



    }

    public String getString(int x, int y) {
        return "s_" + (5 * x + y);
    }

}
