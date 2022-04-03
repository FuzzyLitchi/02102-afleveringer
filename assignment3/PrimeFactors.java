import java.util.Scanner;
import java.util.ArrayList;

public class PrimeFactors {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        // For some reason we never use articles in sentences!
        // Sounds like Arnold Schwarzenegger to me!

        //                       ______
        //                     <((((((\\\
        //                     /      . }\
        //                     ;--..--._|}
        //  (\                 '--/\--'  )
        //   \\                | '-'  :'|
        //    \\               . -==- .-|
        //     \\               \.__.'   \--._
        //     [\\          __.--|       //  _/'--.
        //     \ \\       .'-._ ('-----'/ __/      \
        //      \ \\     /   __>|      | '--.       |
        //       \ \\   |   \   |     /    /       /
        //        \ '\ /     \  |     |  _/       /
        //         \  \       \ |     | /        /
        //   snd    \  \      \        /
        // ENTER INTEGER!! (but in a Austrian accent)

        while (true) {
            System.out.print("Enter integer greater than 1 (0 to terminate): ");
            long input = scanner.nextLong();

            if (input == 0) {
                System.out.println("Goodbye! <3");
                break;
            }

            if (input < 2) {
                // I put emoji in my CLI and if your terminal doesn't support it, that's your problem lol
                System.out.println("I said greater than 1!! 🤬");
                continue;
            }

            // Factorize
            ArrayList<Long> factors = new ArrayList<Long>();
            long product = input;
            long prime = 1;
            while (true) {
                // We could speed this process up by having a prime generator,
                // but instead we just try every number.
                prime++;// = nextPrime();

                while (product % prime == 0) {
                    // We've found a factor!
                    factors.add(prime);
                    product /= prime;
                }

                if (product == 1) {
                    // We've found all the primes
                    break;
                }
            }

            // We print the factors in a human readable way.
            System.out.print("The prime factors of " + input + " are ");

            System.out.print(factors.get(0));
            for (long factor : factors.subList(1, factors.size())) {
                System.out.print(", " + factor);
            }
            System.out.print("\n\n");
        }
    }
}