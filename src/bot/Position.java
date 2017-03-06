package bot;

/**
 * Created by Jonni on 3/5/2017.
 */
public class Position {
    private int x;
    private int y;
    private boolean bomb;
    private int count;

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.bomb = false;
        this.count = 0;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isBomb() {
        return bomb;
    }

    public void addBomb() {
        this.bomb = true;
    }

    public int getCount() {
        return count;
    }

    public void increaseAdjacentBombCount() {
        this.count++;
    }

    @Override
    public int hashCode() {
        return 31 * this.x + this.y;
    }

    @Override
    public boolean equals(Object o) {
        Position other = (Position)o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + "), bomb = " + this.bomb + ", count = " + this.count;
    }
}
