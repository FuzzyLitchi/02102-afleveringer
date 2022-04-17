import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class GameOfLifeMain {
    public static void main(String[] args) throws FileNotFoundException {
        // GameOfLife game = GameOfLife.Random(100);
        GameOfLife game = new GameOfLife("glider_gun.gol");

        // Initalize StdDraw
        StdDraw.setXscale(0, game.size);
        StdDraw.setYscale(0, game.size);
        StdDraw.setPenColor(StdDraw.CYAN);
        StdDraw.setPenRadius(1.0/game.size);

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

    // Read board from file
    public GameOfLife(String path) throws FileNotFoundException {
        Scanner scanner = new Scanner(new File(path));
        
        // Calculate the size from the first line
        String firstLine = scanner.nextLine();
        size = firstLine.split(" ").length;
        board = new int[size][size];

        // Reset scanner and process file.
        scanner = new Scanner(new File(path));
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                board[x][y] = scanner.nextInt();
            }
        }
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
        return board[(x+size)%size][(y+size)%size];
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