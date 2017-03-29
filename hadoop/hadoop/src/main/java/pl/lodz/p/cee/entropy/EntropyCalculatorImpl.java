package pl.lodz.p.cee.entropy;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

/**
 * Some magic EntropyCalculator implementation.
 */
public class EntropyCalculatorImpl implements EntropyCalculator {

    private final List<Predicate<String>> predicates = Arrays.asList(
            Pattern.compile("[a-z]").asPredicate(),
            Pattern.compile("[A-Z]").asPredicate(),
            Pattern.compile("[0-9]").asPredicate(),
            Pattern.compile("[^a-zA-Z0-9]").asPredicate(),
            Pattern.compile(".{10,}").asPredicate(),
            Pattern.compile(".{20,}").asPredicate()
    );
    private final int[] praise = {1, 2, 4, 5, 7, 9, 12};

    // ---------------------------------------------------------------------------------------------------------------

    private final DistanceCalculator ds;

    public EntropyCalculatorImpl(DistanceCalculator ds) {
        this.ds = ds;
    }

    // ---------------------------------------------------------------------------------------------------------------

    /**
     * @param password password to be checked
     * @return int, range 0 (inclusive) to 512 (exclusive)
     */
    @Override
    public int compute(String password) {
        int letters = password.length() + 1;
        int unique = (int) password.chars().distinct().count();

        int tests = (int) predicates.stream()
                .filter(p -> p.test(password))
                .count();

        double distance = IntStream.range(1, password.length())
                .mapToDouble(i -> ds.distance(password.charAt(i), password.charAt(i - 1)))
                .average().orElse(1);

        double magic = letters * Math.sqrt(1.0 * unique / letters) * praise[tests] * (distance / 3);
        return Math.min((int) magic, 511);
    }
}
