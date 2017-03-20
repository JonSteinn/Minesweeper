package csp;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSVariableGrid {
    private MSVariable[][] board;

    public MSVariableGrid(int width, int height) {
        this.board = new MSVariable[height][width];
        for (int j = 0; j < height; j++) {
            for (int i = 0; i < width; i++) {
                this.board[i][j] = new MSVariable(i,j);
            }
        }
    }

    public MSVariable getVariable(int x, int y) {
        return this.board[x][y];
    }
}
