package csp;

/**
 * Created by Jonni on 3/20/2017.
 */
public class MSVariable {

    public static MSVariable fromString(String str) {
        String[] numbers = str.split(",");
        return new MSVariable(
                Integer.parseInt(numbers[0].substring(1, numbers[0].length())),
                Integer.parseInt(numbers[1].substring(0, numbers[1].length() - 1))
        );
    }

    private int x;
    private int y;

    public MSVariable(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }

    @Override
    public boolean equals(Object o) {
        MSVariable other = (MSVariable)o;
        return this.x == other.x && this.y == other.y;
    }

    @Override
    public int hashCode() {
        return this.x * 31 + this.y;
    }
}
