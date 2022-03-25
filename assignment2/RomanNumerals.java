import java.util.Scanner;

public class RomanNumerals {
    public static void main(String args[]) {
        Scanner console = new Scanner(System.in);

        // This sentence is gramatically incorrect! We're missing an article before "positive"
        System.out.print("Enter positive integer to convert: ");
        int integer = console.nextInt();

        String romanNumeral = ToRomanNumerals(integer);

        System.out.println(integer + " = " + romanNumeral);
    }

    static String ToRomanNumerals(int integer) {
        // The maximum number we can convert is 3999
        if (integer > 3999) {
            return "Error: " + integer + " is too big to convert into roman numerals!";
        }
        // We can't represent negative numbers or 0
        if (integer < 1) {
            return "Error: " + integer + " isn't positive! We can only convert positive integers";
        }

        // These arrays include the roman numeral equivilent of it's own index. For example
        // ones[3] == "III" because that's the roman numeral for 3.
        String ones[] = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};
        String tens[] = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
        String hundreds[] = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
        String thousands[] = {"", "M", "MM", "MMM"};

        // We find the roman ones, tens, hundreds and thousands of a given integer,
        // convert each into roman numerals and then concatenate them.
    return thousands[(integer / 1000) % 10] + hundreds[(integer / 100) % 10]
             + tens[(integer / 10) % 10] + ones[integer % 10];
    }
}