package agent;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.constraints.Constraint;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSModel {

    private Model model;
    private Map<Position, Integer> indexMap;
    private IntVar[] vars;

    public MSModel(Set<ConstraintInfo> info, Set<Position> variables) throws ContradictionException {
        this.model = new Model();
        this.indexMap = new HashMap<>();
        this.vars = new IntVar[variables.size()];

        int index = 0;
        for (Position pos : variables) {
            this.vars[index] = this.model.intVar(pos.toString(), 0, 1);
            this.indexMap.put(pos, index);
            index++;
        }
        for (ConstraintInfo c : info) {
            IntVar[] con = new IntVar[c.getUnknownNeighbours().size()];
            index = 0;
            for (Position pos : c.getUnknownNeighbours()) {
                con[index] = this.vars[this.indexMap.get(pos)];
                index++;
            }
            this.model.sum(con, "=", c.getAdjacentBombs()).post();
        }
        this.model.getSolver().propagate();
    }


    public boolean hasBomb(Position p) {
        return containsContradiction(model.arithm(vars[indexMap.get(p)], "=", 0));
    }

    public boolean hasNoBombs(Position p) {
        return containsContradiction(model.arithm(vars[indexMap.get(p)], "=", 1));
    }

    private boolean containsContradiction(Constraint constraint) {
        model.getEnvironment().worldPush();
        model.post(constraint);
        Solution sol = model.getSolver().findSolution();
        model.getEnvironment().worldPop();
        model.unpost(constraint);
        model.getSolver().hardReset();
        return sol == null;
    }
}
