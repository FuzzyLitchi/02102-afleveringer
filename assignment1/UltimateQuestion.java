public class UltimateQuestion {
    public static void main(String[] args) {
        double x = 7.2;
        // This can be reduced to 6 + 5*x = 42 <=> x = 36/5
        // x is not a whole number so we will print "42.0" instead of "42".
        System.out.println(1 + 3 + x + x + x + x + x + 5 / 4 * 2);
    }
}
