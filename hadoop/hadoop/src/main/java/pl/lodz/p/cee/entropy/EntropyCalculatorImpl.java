package pl.lodz.p.cee.entropy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;

/**
 * Some magic EntropyCalculator implementation.
 */
public class EntropyCalculatorImpl implements EntropyCalculator {

    private final List<Predicate<String>> predicates = Arrays.asList(
            Pattern.compile("[a-z]").asPredicate(),
            Pattern.compile("[A-Z]").asPredicate(),
            Pattern.compile("[0-9]").asPredicate(),
            Pattern.compile("[^a-zA-Z0-9]").asPredicate(),
            Pattern.compile(".{10,}").asPredicate()
    );
    private final int[] praise = {1, 3, 5, 9, 13, 14};

    /**
     * @param password password to be checked
     * @return int, range 0 to 255 (both inclusive)
     */
    @Override
    public int compute(String password) {
        int letters = password.length() + 1;
        int unique = (int) password.chars().distinct().count();
        int tests = (int) predicates.stream().filter(p -> p.test(password)).count();
        double magic = letters * Math.sqrt(1.0 * unique / letters) * praise[tests];
        return Math.min((int) magic, 255);
    }
}
