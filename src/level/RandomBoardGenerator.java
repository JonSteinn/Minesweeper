package level;

import java.util.Random;

/**
 * Created by Jonni on 3/5/2017.
 */
public class RandomBoardGenerator {

    private static final int SPLIT_BOUNDARY = 5;
    private Random random;

    public RandomBoardGenerator() {
        this.random = new Random();
    }

    public Board create(int width, int height, int bombs, boolean noSurrounded, boolean spreadEven) {
        return (noSurrounded && spreadEven) ?
                createEvenSpreadNoSurrounded(width, height, bombs) :
                (noSurrounded ?
                        createNoSurrounded(width, height, bombs) :
                        (spreadEven ?
                                createEvenSpread(width, height, bombs) :
                                create(width, height, bombs)
                        )
                );
    }


    private Board create(int width, int height, int bombs) {
        Board board = new Board(width, height);
        while (board.getBombCount() < bombs) {
            int x = this.random.nextInt(width);
            int y = this.random.nextInt(height);
            board.addBomb(x, y);
        }
        return board;
    }

    private Board createNoSurrounded(int width, int height, int bombs) {
        Board board = new Board(width, height);
        while (board.getBombCount() < bombs) {
            int x = this.random.nextInt(width);
            int y = this.random.nextInt(height);
            if (surrounded(board, x, y)) continue;
            board.addBomb(x, y);
        }
        return board;
    }

    private Board createEvenSpread(int width, int height, int bombs) {
        Board board = new Board(width, height);
        spreadEvenHelper(board, 0, width - 1, 0, height - 1, bombs, false);
        return board;
    }

    private Board createEvenSpreadNoSurrounded(int width, int height, int bombs) {
        Board board = new Board(width, height);
        spreadEvenHelper(board, 0, width - 1, 0, height - 1, bombs, true);
        return board;
    }

    private void spreadEvenHelper(Board board, int minX, int maxX, int minY, int maxY, int bombs, boolean noSurrounded) {
        if ((maxX - minY > SPLIT_BOUNDARY) && (maxY - minY > SPLIT_BOUNDARY)) {
            int halfX = (maxX - minX) >> 1;
            int halfY = (maxY - minY) >> 1;
            int quartBombs = bombs >> 2;
            int bombReminder = bombs % 4;
            spreadEvenHelper(board, minX, minX + halfX, minY, minY + halfY,
                    bombReminder > 0 ? quartBombs + 1: quartBombs, noSurrounded);
            spreadEvenHelper(board, minX, minX + halfX , minY + halfY + 1, maxY,
                    bombReminder > 1 ? quartBombs + 1 : quartBombs, noSurrounded);
            spreadEvenHelper(board, minX + halfX + 1, maxX, minY, minY + halfY,
                    bombReminder > 2 ? quartBombs + 1 : quartBombs, noSurrounded);
            spreadEvenHelper(board, minX + halfX + 1, maxX, minY + halfY + 1, maxY,
                    quartBombs, noSurrounded);
        } else if (maxX - minX > SPLIT_BOUNDARY) {
            int half = (maxX - minX) >> 1;
            int halfBombs = bombs >> 1;
            spreadEvenHelper(board, minX, minX + half, minY, maxY, bombs - halfBombs, noSurrounded);
            spreadEvenHelper(board, minX + half + 1, maxX, minY, maxY, halfBombs, noSurrounded);
        } else if (maxY - minY > SPLIT_BOUNDARY) {
            int half = (maxY - minY) >> 1;
            int halfBombs = bombs >> 1;
            spreadEvenHelper(board, minX, maxX, minY, minY + half, bombs - halfBombs, noSurrounded);
            spreadEvenHelper(board, minX, maxX, minY + half + 1, maxY, halfBombs, noSurrounded);
        } else {
            int goalBombs = board.getBombCount() + bombs;
            if (noSurrounded) {
                while (board.getBombCount() < goalBombs) {
                    int x = intervalRandom(minX, maxX);
                    int y = intervalRandom(minY, maxY);
                    if (surrounded(board, x, y)) continue;
                    board.addBomb(x, y);
                }
            } else {
                while (board.getBombCount() < goalBombs) {
                    board.addBomb(intervalRandom(minX, maxX), intervalRandom(minY, maxY));
                }
            }
        }
    }

    private boolean surrounded(Board board, int x, int y) {
        for (int i = x - 1; i < x + 2; i++) {
            for (int j = y - 1; j < y + 2; j++) {
                if (!board.outOfBounds(i,j) && !board.containsBomb(i,j)) {
                    return false;
                }
            }
        }
        return true;
    }

    private int intervalRandom(int a, int b) {
        return this.random.nextInt(b-a+1)+a;
    }
}
