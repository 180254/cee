package pl.lodz.p.cee.entropy;

import java.util.Arrays;
import java.util.List;

public class DistanceCalculatorImpl implements DistanceCalculator {

    private final List<char[]> chars = Arrays.asList(
            "`~1234567890-=~!@#$%^&*()_+".toCharArray(),
            "\tqwertyuiop[]{}".toCharArray(),
            "asdfghjkl;'\\:|".toCharArray(),
            "\\zxcvbnm,./<>?".toCharArray(),
            " ".toCharArray()
    );

    // ---------------------------------------------------------------------------------------------------------------

    @Override
    public double distance(char a, char b) {
        Point p1 = pos(Character.toLowerCase(a));
        Point p2 = pos(Character.toLowerCase(b));
        return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    // ---------------------------------------------------------------------------------------------------------------

    private Point pos(char c) {
        for (int i = 0; i < chars.size(); i++) {
            for (int j = 0; j < chars.get(i).length; j++) {
                if (chars.get(i)[j] == c) {
                    return new Point(i, j);
                }
            }
        }

        return new Point(c % 4, c % 16);
    }

    // ---------------------------------------------------------------------------------------------------------------

    private static class Point {
        public final int x;
        public final int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
