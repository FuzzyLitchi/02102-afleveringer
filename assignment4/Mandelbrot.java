import java.util.Random;
import java.util.Scanner;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;

class Complex {
    private double real;
    private double imaginary;

    public Complex(double re, double im) {
        this.real = re;
        this.imaginary = im;
    }

    // Clones the other complex numbers
    public Complex(Complex z) {
        this.real = z.getRe();
        this.imaginary = z.getIm();
    }

    public double getRe() {
        return this.real;
    }

    public double getIm() {
        return this.imaginary;
    }

    // Returns the length/absolute value of the complex number
    public double abs() {
        // sqrt( Re(z)^2 + Im(z)^2 )
        return Math.sqrt((this.real*this.real)+(this.imaginary*this.imaginary));
    }

    // Adds two complex numbers together and returns the result
    public Complex plus(Complex other) {
        return new Complex(this.real + other.real, this.imaginary + other.imaginary);
    }

    // Multiplies two complex numbers together and returns the result
    public Complex times(Complex other) {
        // We recall that
        // (a+bi) * (c+di) = (ac-bd) + (bc+ad)i
        return new Complex(
            this.real * other.real - this.imaginary * other.imaginary, // ac-bd
            this.imaginary * other.real + this.real * other.imaginary  // bc+ad
        );
    }

    @Override
    public String toString() {
        if (imaginary >= 0) {
            return real + " + " + imaginary + "i";
        } else {
            return real + " - " + Math.abs(imaginary) + "i";
        }
    }
}

public class Mandelbrot {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Handle input
        System.out.println("Input a complex number to center the rendering around.");
        System.out.print("Real component: ");
        double midpoint_real = scanner.nextDouble();
        System.out.print("Imaginary component: ");
        double midpoint_imaginary = scanner.nextDouble();
        System.out.println("Centering around " + new Complex(midpoint_real, midpoint_imaginary));

        System.out.print("Input the side length of the square area that we're rendering\n (as a real number): ");
        double side_length = scanner.nextDouble();

        // Init StdDraw
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        StdDraw.setPenRadius(1.0/GRID_SIZE);
        StdDraw.setPenColor(StdDraw.RED);

        // Load colors
        Color[] colors;
        try {
            colors = loadColorsFromFile("blues.mnd");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }

        // Grid calculations
        double cell_width = side_length/(GRID_SIZE-1);

        StdDraw.show(0);
        // We iterate over a grid where a and b are the indexes in the horizontal
        // and vertical direction repectively.
        for (int a = 0; a < GRID_SIZE; a++) {
            for (int b = 0; b < GRID_SIZE; b++) {
                // We scale the indexes into points around the midpoints that range from
                // midpoint-side_length/2 to midpoint+side_length/2
                //
                // +-------------------+ \
                // |                   | |
                // |                   | |
                // |                   | |
                // |         o         | | side length
                // |         ^midpoint | |
                // |                   | |
                // |                   | |
                // +-------------------+ /
                //
                // \____side length____/
                //
                Complex z = new Complex(
                    midpoint_real - side_length/2 + cell_width * a,
                    midpoint_imaginary - side_length/2 + cell_width * b
                );
                int depth = iterate(z);

                StdDraw.setPenColor(colors[depth]);
                // Again we scale the index, but this time to be between 0 and 1.
                // However, we make sure we're always a pen radius away from the edge of the screen.
                // Which is what the + 0.5 does.
                StdDraw.point((a+0.5)/GRID_SIZE, (b+0.5)/GRID_SIZE);
            }
        }
        StdDraw.show(0);
    }

    static private final int MAX = 255;

    static private final int GRID_SIZE = 512;

    public static int iterate(Complex z0) {
        Complex z = new Complex(z0);
        for (int i = 0; i < MAX; i++) {
            if (z.abs() > 2.0) {
                return i;
            }

            z = z.times(z).plus(z0);
        }
        return MAX;
    }

    static Color[] loadColorsFromFile(String path) throws FileNotFoundException {
        Color[] colors = new Color[256];
        File file = new File(path);
        Scanner fileScanner = new Scanner(file);

        for (int i = 0; i < 256; i++) {
            colors[i] = new Color(
                fileScanner.nextInt(),
                fileScanner.nextInt(),
                fileScanner.nextInt()
            );
        }

        fileScanner.close();
        return colors;
    }

    static Color[] loadRandomColors() {
        Random random = new Random();

        Color[] colors = new Color[256];
        for (int i = 0; i < 256; i++) {
            colors[i] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        return colors;
    }
}
