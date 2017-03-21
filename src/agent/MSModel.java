package agent;

import org.chocosolver.solver.Model;
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

    public Model model;
    public Map<Position, Integer> indexMap;
    public IntVar[] vars;

    public MSModel(Set<ConstraintInfo> info, Set<Position> variables) throws ContradictionException {
        model = new Model();
        indexMap = new HashMap<>();
        vars = new IntVar[variables.size()];

        int index = 0;
        for (Position pos : variables) {
            vars[index] = model.intVar(pos.toString(), 0, 1);
            indexMap.put(pos, index);
            index++;
        }
        for (ConstraintInfo c : info) {
            IntVar[] con = new IntVar[c.unknownNeighbours.size()];
            index = 0;
            for (Position pos : c.unknownNeighbours) {
                con[index] = vars[indexMap.get(pos)];
                index++;
            }
            model.sum(con, "=", c.adjacentBombs);
        }

        model.getSolver().propagate();
    }


    public boolean hasBomb(Position p) {
        return containsContradiction(model.arithm(vars[indexMap.get(p)], "=", 0));
    }

    public boolean hasNoBombs(Position p) {
        return containsContradiction(model.arithm(vars[indexMap.get(p)], "=", 1));
    }

    private boolean containsContradiction(Constraint constraint) {
        model.getEnvironment().worldPush();
        constraint.post();

        try {
            model.getSolver().propagate();
            return model.getSolver().findSolution() == null;
        } catch (ContradictionException e) {
            model.getSolver().getEngine().flush();
            return true;
        } finally {
            model.getEnvironment().worldPop();
            model.unpost(constraint);
        }
    }
}
