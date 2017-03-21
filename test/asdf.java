import agent.MSAgent;
import agent.Position;
import level.Board;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by Jonni on 3/19/2017.
 */
public class asdf {
    @Test
    public void foo() {

        Board b = new Board(5,5);
        b.addBomb(0,0);
        b.addBomb(1,1);
        b.addBomb(0,1);
        System.out.println(b);

        MSAgent agent = new MSAgent(5, 5, 3);
        System.out.println(agent.nextMove()); // Ignore first and hand pick

        System.out.println(Arrays.deepToString(agent.board.board));

        agent.sendBackResult(agent.grid.getVariable(4,4), 0);

        System.out.println(Arrays.deepToString(agent.board.board));

        while (!agent.pendingMoves.isEmpty()) {
            Position p = agent.nextMove();
            System.out.println(p + ": " + b.adjacentBombs(p.getX(), p.getY()));
            agent.sendBackResult(p, b.adjacentBombs(p.getX(), p.getY()));
        }

        System.out.println(Arrays.deepToString(agent.board.board));

        Position n = agent.nextMove();
        System.out.println(n);
        agent.sendBackResult(n, b.adjacentBombs(n.getX(), n.getY()));

        System.out.println(Arrays.deepToString(agent.board.board));
        System.out.println(agent.pendingMoves);
    }
}