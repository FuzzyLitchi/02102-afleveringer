import java.util.Random;

public class GameOfLifeMain {
    public static void main(String[] args) {
        GameOfLife game = GameOfLife.Random(100);

        StdDraw.setXscale(0, 100);
        StdDraw.setYscale(0, 100);
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.setPenRadius(1.0/100);

        while (true) {
            StdDraw.show(0);
            StdDraw.clear();
            game.draw();
            StdDraw.show(100);

            game = game.nextState();
        }
    }
}

class GameOfLife {
    int size;
    int[][] board;

    public GameOfLife(int size) {
        this.size = size;
        this.board = new int[size][size];
    }

    // We only accept square state
    GameOfLife(int[][] initialState) {
        this.size = initialState.length;
        this.board = initialState;
    }

    public static GameOfLife Random(int size) {
        Random random = new Random();
        int[][] board = new int[size][size];
        
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                board[x][y] = random.nextInt(2);
            }
        }

        return new GameOfLife(board);
    }

    public GameOfLife nextState() {
        int[][] futureState = new int[size][size];

        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                int count = liveNeighbours(x, y);
                
                if (board[x][y] == 1) {
                    // Cell is alive, and will only continue to be alive if there are 2 or 3 neighbors.
                    if (count == 2 || count == 3) {
                        futureState[x][y] = 1;
                    } else {
                        futureState[x][y] = 0;
                    }
                } else {
                    // Cell is dead, but can be revived if there are 3 neighbors.
                    if (count == 3) {
                        futureState[x][y] = 1;
                    } else {
                        futureState[x][y] = 0;
                    }
                }
            }
        }

        return new GameOfLife(futureState);
    }

    int getState(int x, int y) {
        if (x >= size || x < 0 || y >= size || y < 0) {
            return 0;
        }
        return board[x][y];
    }

    int liveNeighbours(int x, int y) {
        // We sum the spots marked x where o is the target location
        // xxx
        // xox
        // xxx

        return getState(x-1, y+1) + getState(x, y+1) + getState(x+1, y+1)
             + getState(x-1, y) + getState(x+1, y)
             + getState(x-1, y-1) + getState(x, y-1) + getState(x+1, y-1);
    }

    void draw() {
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                if (board[x][y] == 1) {
                    StdDraw.point(x+0.5, y+0.5);
                }
            }
        }
    }
}