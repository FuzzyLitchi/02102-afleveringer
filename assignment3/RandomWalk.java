import java.util.Random;
import java.util.Scanner;

public class RandomWalk {
    Random random = new Random();

    int size;

    int x;
    int y;

    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter size of grid: ");
        RandomWalk randomWalk = new RandomWalk(scanner.nextInt());

        // And here we have the rarely seen "do-while loop". Why does Java
        // have this specialization of a loop? If we have loops that break
        // at the begining and loops that break at the end, why don't we
        // have loops that break in the middle? Our best scholars still
        // can't answer these questions. However, we can marvel at its
        // monstrosity and deviance.
        int steps = 0;

        // We print our position and take a step until we're out of bounds.
        randomWalk.outputPosition();
        do {
            randomWalk.takeStep();
            steps++;

            randomWalk.outputPosition();
        }
        while (!randomWalk.isOutOfBound());

        System.out.println("\nTotal number of steps = " + steps);
    }

    RandomWalk(int size) {
        this.size = size;

        x = 0;
        y = 0;

        StdDraw.setXscale(-size, size);
        StdDraw.setYscale(-size, size);
        StdDraw.setPenRadius(0.5/size);
    }

    void outputPosition() {
        // We print where we are to the console
        System.out.println("Position = (" + x + "," + y + ")");

        // And in the drawing.
        StdDraw.point(x, y);
    }

    // Take a step in a random direction
    void takeStep() {
        // 0 is east
        // 1 is north
        // 2 is west
        // 3 is south
        int direction = random.nextInt(0, 4);
        switch (direction) {
            case 0:
                x++;
                break;

            case 1:
                y++;
                break;
                
            case 2:
                x--;
                break;
            
            case 3:
                y--;
                break;
        }
    }

    boolean isOutOfBound() {
        return x < -size || x > size || y < -size || y > size;
    }
}
