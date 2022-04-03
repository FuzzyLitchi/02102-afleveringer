import java.util.Random;
import java.util.Scanner;

public class RaceTrack {

    static final int WIDTH = 20;
    static final int HEIGHT = 20;

    // I FUCKING LOVE UNICODE
    static final String MOVEMENT_TEXT_ART = 
    "┌───┬───┬───┐\n" +
    "│ 7 │ 8 │ 9 │\n" +
    "├───┼───┼───┤\n" +
    "│ 4 │ 5 │ 6 │\n" +
    "├───┼───┼───┤\n" +
    "│ 1 │ 2 │ 3 │\n" +
    "└───┴───┴───┘";

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Draw map/background

        // The axis are like a normal math coordinate system
        // (0,0) is at the bottom left, x grows to the right and y grows up
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);

        StdDraw.clear(StdDraw.LIGHT_GRAY);
        // Draw the lattice
        StdDraw.setPenRadius(1.0/1000);
        for (int x = 0; x < WIDTH; x++) {
            StdDraw.line(x, 0, x, HEIGHT);
        }
        for (int y = 0; y < WIDTH; y++) {
            StdDraw.line(0, y, WIDTH, y);
        }

        // Draw inner square
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledSquare(WIDTH/2.0, HEIGHT/2.0, 5.0);

        // Draw inner border
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(1.0/200);
        StdDraw.line(5, 15, 15, 15);
        StdDraw.line(15, 15, 15, 5);
        StdDraw.line(15, 5, 5, 5);
        StdDraw.line(5, 5, 5, 15);

        // Draw start line
        StdDraw.setPenColor(StdDraw.GREEN);
        StdDraw.line(10, 15, 10, 20);


        // Player variables
        Vector velocity = new Vector(0, 0);
        Vector position = new Vector(10, 17);

        // Count the amount of moves the player has made
        int moves = 0;

        // Game loop
        while (true) {
            System.out.println(MOVEMENT_TEXT_ART);
            System.out.print("Select the direction of acceleration: ");
            int input = scanner.nextInt();
            if (input < 1 || input > 9) {
                System.out.println("Enter an integer between 1 and 9");
                continue;
            }

            // Move is valid, so we count it
            moves++;

            Vector acceleration = Vector.fromUserInput(input);

            Vector oldPosition = position.clone();
            // We integrate the acceleration into the velocity and position
            velocity.add(acceleration);
            position.add(velocity);

            // Draw the line from the old position to the new
            StdDraw.setPenColor(StdDraw.BOOK_BLUE);
            StdDraw.line(
                oldPosition.x,
                oldPosition.y,
                position.x,
                position.y
            );

            // Check if we crashed
            if (position.isInBox(new Vector(5, 5), 10, 10) || !position.isInBox(new Vector(0, 0), WIDTH, HEIGHT)) {
                System.out.println("You crashed! Better luck next time.");
                break;
            }

            // Check we finished the track. We do this by checking if the previous position
            // was on the left side of the goal and the new position is on the right. We
            // can cheat pretty easily by moving left and then right, but it works if you're
            // honest.
            if (oldPosition.isInBox(new Vector(0, 10), 10, 10) && position.isInBox(new Vector(10, 10), 10, 10)) {
                System.out.println("Lap completed in " + moves + " moves!");
                moves = 0;
            }
        }

    }
}

class Vector {
    public int x;
    public int y;

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Adds the other vector to this one
    public void add(Vector other) {
        this.x += other.x;
        this.y += other.y;
    }

    public Vector clone() {
        return new Vector(this.x, this.y);
    }

    public boolean isInBox(Vector position, int width, int height) {
        return x >= position.x && x <= position.x + width &&
            y >= position.y && y <= position.y + height;
    }

    public static Vector fromUserInput(int selection) {
        switch (selection) {
            case 1:
                return new Vector(-1, -1);
            case 2:
                return new Vector(0, -1);
            case 3:
                return new Vector(1, -1);
            case 4:
                return new Vector(-1, 0);
            case 5:
                return new Vector(0, 0);
            case 6:
                return new Vector(1, 0);
            case 7:
                return new Vector(-1, 1);
            case 8:
                return new Vector(0, 1);
            case 9:
                return new Vector(1, 1);
            default:
                // Invalid input
                // TODO: throw exception
                return new Vector(10000, 10000);
        }
    }
}