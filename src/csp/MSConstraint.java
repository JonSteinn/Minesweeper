package csp;

import org.chocosolver.solver.Model;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSConstraint {
    private Set<MSVariable> variables;
    private int value;

    public MSConstraint(int value, MSVariable... positions) {
        this.variables = new HashSet<>();
        this.value = value;
        Collections.addAll(this.variables, positions);
    }

    public void addVariable(MSVariable position) {
        this.variables.add(position);
    }

    public void removeVariable(MSVariable position) {
        this.variables.remove(position);
    }

    public boolean containsVariable(MSVariable position) {
        return this.variables.contains(position);
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void decreaseValueBy(int amount) {
        this.value -= amount;
    }

    @Override
    public String toString() {
        return "sum" + Arrays.toString(this.variables.toArray()) + " = " + this.value;
    }

    @Override
    public boolean equals(Object o) {
        return this.variables.equals(((MSConstraint)o).variables);
    }

    @Override
    public int hashCode() {
        return this.variables.hashCode();
    }
}
