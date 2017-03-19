package agent;

import java.util.Random;

/**
 * Created by Jonni on 3/19/2017.
 */
public class RandomAgent {
    Random generator = new Random();

    public RandomAgent() {

    }

    public int[] nextMove(int w, int h) {
        return new int[]{generator.nextInt(w), generator.nextInt(h)};
    }

}
