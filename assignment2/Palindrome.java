import java.util.Scanner;

public class Palindrome {
    public static void main(String args[]) {
        Scanner console = new Scanner(System.in);

        System.out.print("Enter line to check: ");
        String text = console.nextLine();

        // Check if the input is a palindrome and report approprietly.
        if (isPalindrome(text)) {
            System.out.println("\"" + text + "\" is a palindrome!");
        } else {
            System.out.println("\"" + text + "\" is not a palindrome!");
        }
    }

    static boolean isPalindrome(String text) {
        // We make every letter lowercase and remove all non-letter characters
        String parsedtext = text.toLowerCase();
        parsedtext = parsedtext.replaceAll("[^a-z]+", "");

        int length = parsedtext.length();
        for (int i = 0; i < length/2; i++) {
            // Check if the i'th character matches the i'th character starting from the left.
            if (parsedtext.charAt(i) != parsedtext.charAt(length - 1 - i)) {
                // If they don't, it's not a palindrome
                return false;
            }
        }

        return true;
    }
}