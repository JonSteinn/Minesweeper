package agent;

import org.chocosolver.solver.Model;
import org.chocosolver.solver.Solution;
import org.chocosolver.solver.exception.ContradictionException;
import org.chocosolver.solver.variables.IntVar;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Jonni on 3/22/2017.
 */
public class ProbabilityModel {

    private Model model;
    private Map<Position, IntVar> varMap;
    private Map<IntVar, Position> posMap;

    public ProbabilityModel(Set<ConstraintInfo> info, Set<Position> variables, Set<Position> varCollection) throws ContradictionException {
        this.model = new Model();
        this.varMap = new HashMap<>();

        for (Position pos : variables) {
            this.varMap.put(pos, this.model.intVar(pos.toString(), 0, 1));
            varCollection.add(pos);
        }
        for (ConstraintInfo c : info) {
            IntVar[] con = new IntVar[c.getUnknownNeighbours().size()];
            int index = 0;
            for (Position pos : c.getUnknownNeighbours()) {
                con[index] = this.varMap.get(pos);
                index++;
            }
            this.model.sum(con, "=", c.getAdjacentBombs()).post();
        }
        this.model.getSolver().propagate();
    }

    public int getProbabilities(Map<Position, Double> pMap) {
        int minBombs = Integer.MAX_VALUE;
        for (Position position : this.varMap.keySet()) pMap.put(position, 0.0);

        for (Solution solution : this.model.getSolver().findAllSolutions()) {
            int bombsThisSolution = 0;
            for (Position position : this.varMap.keySet()) {
                int value = solution.getIntVal(this.varMap.get(position));
                if (value == 1) {
                    pMap.put(position, pMap.get(position) + 1);
                    bombsThisSolution++;
                }
            }
            if (minBombs > bombsThisSolution) minBombs = bombsThisSolution;
        }

        long totalSolutions = this.model.getSolver().getSolutionCount();
        for (Position position : this.varMap.keySet()) {
            pMap.put(position, 100.0 * pMap.get(position) / totalSolutions);
        }

        return minBombs;
    }
}
