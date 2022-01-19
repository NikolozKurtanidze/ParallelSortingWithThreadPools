package pgdp.threads;

import java.util.Random;

public class Util {
    static Random r;

    public static Comparable[] randomArrayWithSeed(int n, long seed) {
        r = new Random(seed);

        final Integer[] result = new Integer[n];
        for (int i = 0; i < n; i++) {
            result[i] = r.nextInt();
        }

        return result;
    }
}
