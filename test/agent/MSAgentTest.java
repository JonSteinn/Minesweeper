package agent;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.variables.IntVar;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Jonni on 3/22/2017.
 */
public class MSAgentTest {


    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void foo() {
        Model m = new Model();
        IntVar i = m.intVar("a", 0,1);
        IntVar j = m.intVar("b", 0,1);
        System.out.println(i.hashCode());
        System.out.println(j.hashCode());
        m.post(m.sum(new IntVar[]{i,j}, "=", 1));
        for (Solution sol : m.getSolver().findAllSolutions()) {
            System.out.println(sol.getIntVal(i));
            System.out.println(sol.getIntVal(j));
        }
        System.out.println(m.getSolver().getSolutionCount());


    }

}