package agent;

import java.util.*;

/**
 * Created by Jonni on 3/20/2017.
 */
public class ConstraintGroups {
    public Map<Set<ConstraintInfo>, Set<Position>> groups;

    public ConstraintGroups(PerspectiveBoard board) {
        this.groups = new HashMap<>();
        for (ConstraintInfo info : board.constraintPositions.values()) add(info);
    }

    public void add(ConstraintInfo info) {
        Stack<Set<ConstraintInfo>> toMerge = new Stack<>();
        for (Map.Entry<Set<ConstraintInfo>, Set<Position>> entry : groups.entrySet()) {
            for (Position variable : info.unknownNeighbours) {
                if (entry.getValue().contains(variable)) {
                    toMerge.push(entry.getKey());
                    break;
                }
            }
        }
        if (toMerge.isEmpty()) {
            Set<ConstraintInfo> newSet = new HashSet<>();
            newSet.add(info);
            groups.put(newSet, info.unknownNeighbours);
        } else {
            merge(toMerge, info);
        }
    }

    public void merge(Stack<Set<ConstraintInfo>> toMerge, ConstraintInfo info) {
        Set<ConstraintInfo> keySet = new HashSet<>();
        Set<Position> valueSet = new HashSet<>();
        while (!toMerge.isEmpty()) {
            Set<ConstraintInfo> temp = toMerge.pop();
            valueSet.addAll(groups.get(temp));
            keySet.addAll(temp);
            groups.remove(temp);
        }
        keySet.add(info);
        valueSet.addAll(info.unknownNeighbours);
        groups.put(keySet, valueSet);
        // TODO: simplify when adding new (a1+a2+a_3+a_4 = 2 should become a_3 + a_4 = 1 if a_1+a_2=1 is added)
    }


}
