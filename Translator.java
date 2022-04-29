import java.util.Locale;
import java.util.Scanner;

public class Translator {
    /**
     * The string achieved (at least hypothetically).
     */
    private String word;

    /**
     * Constructor for {@code Translator} {@code class}, saving {@code str} as the {@code private} variable {@link #word}.
     * @param str {@code String} which represents the counter.
     *                          It may be changed later by the {@link #setWord(String)} method.
     * @throws RuntimeException {@code str} must <b>not</b> contain any characters other than letters
     *                          (see {@link #checkOnlyLetters(String)}).
     */
    public Translator(String str) {
        checkOnlyLetters(str);
        word = str.toUpperCase(Locale.ROOT);
    }

    /**
     * Auxiliary method to check whether {@code str} is made of only letters. <br>
     * <i>Note: The letter Ñ is included in this category.</i>
     * @param str {@code String} which represents the counter.
     * @throws RuntimeException {@code str} must <b>not</b> contain any characters other than letters.
     */
    private void checkOnlyLetters(String str) {
        for (char l : str.toUpperCase(Locale.ROOT).toCharArray())
            if ((l < 'A' || l > 'Z') && l != 'Ñ')
                throw new RuntimeException("<!> Argument must not contain any characters other than letters.");
    }

    /**
     * Getter for private variable {@link #word}.
     * @return variable {@code word} as a {@code String}.
     */
    public String getWord() {
        return word;
    }

    /**
     * Setter for private variable {@link #word}.
     * @param str {@code String} which represents the new counter.
     * @throws RuntimeException {@code str} must <b>not</b> contain any characters other than letters
     *                          (see {@link #checkOnlyLetters(String)}).
     */
    public void setWord(String str) {
        str = str.toUpperCase(Locale.ROOT);
        checkOnlyLetters(str);
        this.word = str;
    }

    /**
     * Calculates the number of iterations needed to go from the private variable {@link #word} to the
     * parameter {@code wordTo}, with the help of {@link #toNum(String)} and {@link #toNum()}.
     * @param wordTo {@code String} to go to.
     * @return The number of iterations which separate the words.
     * @throws RuntimeException {@code wordTo} must <b>not</b> contain any characters other than letters
     *                          (see {@link #checkOnlyLetters(String)}).
     */
    public long distanceTo(String wordTo) {
        return toNum(wordTo) - toNum();
    }

    /**
     * Calculates the decimal number which represents the number of iterations needed to achieve
     * {@code word} from scratch, through {@link #toNum(String)}.
     * @return The integer associated to the private variable.
     */
    public long toNum() {
        return toNum(word);
    }

    /**
     * Generalization of method {@link #toNum()} to simplify the calculation of {@link #distanceTo(String)}.
     * @param s The {@code String} to achieve.
     * @return The decimal number which represents the number of iterations needed to achieve it from scratch.
     * @throws RuntimeException {@code s} must <b>not</b> contain any characters other than letters
     *                          (see {@link #checkOnlyLetters(String)}).
     */
    private long toNum(String s) {
        checkOnlyLetters(s);
        int res = 0, j = s.length() - 1;
        for (char c : s.toUpperCase(Locale.ROOT).toCharArray()) {
            if (c == 'Ñ') res += ('N' - 'A' + 2) * Math.pow(27, j); //"Inserts" the letter 'Ñ' in between 'N' and 'O'.
            else if (c < 'O') res += (c - 'A' + 1) * Math.pow(27, j);
            else res += (c - 'A' + 2) * Math.pow(27, j);            //One more to take into account the letter 'Ñ'.
            j--;
        }
        return res;
    }
    
    public static void main(String[] args) {
        try (Scanner sc = new Scanner(System.in)) {
            Translator test = new Translator("");
            while (true) {
                System.out.print("Enter the string to translate (or 0 to exit): ");
                String s = sc.next();
                if (s.equals("0")) break;
                test.setWord(s);
                System.out.println("The value of the string \"" + test.getWord() + "\" is: " + test.toNum());

                System.out.print("Enter the second string (or 0 to exit): ");
                String s2 = sc.next().toUpperCase(Locale.ROOT);
                if (s2.equals("0")) break;
                long d = test.distanceTo(s2);
                if (d > 0) System.out.println(d + " iterations are needed to go from " + test.getWord() + " to " + s2 + "\n");
                else {
                    System.out.print("You already got to the string " + s2 + "!");
                    System.out.println(d == 0 ? "\n" : " You got it " + -d + " iterations ago.\n");
                }
            }
        } catch (RuntimeException e) {
            System.err.println(e.getMessage());
        }
    }
}
