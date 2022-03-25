import java.util.Random;
import java.util.Scanner;

public class BuffonsNeedle {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        System.out.print("Enter number of iterations: ");

        int throw_count = scanner.nextInt();
        int sucesses = 0;

        for (int i = 0; i < throw_count; i++) {
            // Our strategy is that we pick a random x midpoint and a random angle.
            // Then we calculate how wide an axis-alligned box that fully encapsulates
            // the neelde would be, and  the left hand x-coordinate of said box.
            // Lastly, we count whether or not it's touching an edge.
            
            // There's a vertical line at x = 0 and x = 2
            // We uniformly select x between 0..2 and angle between 0..pi

            double midpoint = random.nextDouble() * 2;
            double angle = random.nextDouble() * Math.PI;
            
            // If the needle had a length other than 1 we would multiply it here
            double width = Math.abs(Math.cos(angle));
            double left_corner = midpoint-width/2;
            
            // Check if we're overlapping either of the corners
            boolean overlaps = overlaps_point(0.0, left_corner, width) || overlaps_point(2.0, left_corner, width);

            if (overlaps) {
                sucesses++;
            }
        }

        System.out.println("Out of " + throw_count + " throws, " + sucesses + " hit an edge.");
        System.out.println("Which means we estimate π to be " + (double)throw_count/(double)sucesses);
    }

    static boolean overlaps_point(double x_point, double left_corner, double width) {
        return left_corner < x_point && (left_corner+width) > x_point;
    }
}