package library;

import java.util.ArrayList;
import java.util.Arrays;

public class MathExtentions {
    public static final long DEFAULT_MOD = 1_000_000_007;

    public static long modInverse_For_M_Prime(final long a, final long m) {
        return powerMod(a, m - 2, m);
    }

    public static long powerMod(final long x, final long y, final long m) {
        if (y == 0)
            return 1;

        long p = powerMod(x, y / 2, m) % m;
        p = (p * p) % m;

        if (y % 2 == 0)
            return p;
        else
            return (x * p) % m;
    }

    public static long gcd(final long a, final long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    public static long[][] preComputeNChooseK_MOD_Mod(final int maxn, final long MOD) {
        long[][] res = new long[maxn + 1][];
        for (int i = 0; i <= maxn; i++) {
            res[i] = new long[i + 1];
            res[i][0] = 1;
            res[i][i] = 1;
        }
        for (int i = 2; i < maxn; i++) {
            for (int j = 1; j <= i >> 1; j++) {
                long ress = (res[i - 1][j] + res[i - 1][j - 1]);
                if (ress >= MOD) ress -= MOD;
                res[i][j] = ress;
                res[i][i - j] = ress;
            }
        }
        return res;
    }

    private static int[] getFactorial(final int limit, final int checkLimit) {
        int[] res = new int[limit + 1];
        Arrays.fill(res, 1);

        for (int i = 4; i <= limit; i += 2) {
            res[i] = 2;
        }
        for (int i = 3; i <= checkLimit; i += 2) {
            for (int j = i + i; j <= limit; j += i) {
                res[j] = i;
            }
        }
        return res;
    }

    public static int[] getFactorial_Biggest(final int limit) {
        return getFactorial(limit, limit / 2);
    }

    public static int[] getFactorial(final int limit) {
        return getFactorial(limit, (int) Math.sqrt((double) limit));
    }

    public static int[] primes(final int limit) {
        int checkLimit = (int) Math.sqrt((double) limit);
        boolean[] res = new boolean[limit + 1];
        for (int i = 4; i <= limit; i += 2) {
            res[i] = true;
        }
        for (int i = 3; i <= checkLimit; i += 2) {
            for (int j = i + i; j <= limit; j += i) {
                res[j] = true;
            }
        }
        int size = 0;
        for (int i = 2; i <= limit; i++) {
            if (!res[i]) size++;
        }
        int[] ress = new int[size];
        int j = 0;
        if (limit >= 2) ress[j++] = 2;
        for (int i = 3; i <= limit; i += 2) {
            if (!res[i]) ress[j++] = i;
        }
        return ress;
    }

    public static int[] primes_SieveOfAtkin(final int limit) {
        int myLimit = Math.max(limit, 3);
        boolean[] sieve = new boolean[myLimit + 1];
        int limitSqrt = (int) Math.sqrt((double) myLimit);
        sieve[0] = false;
        sieve[1] = false;
        sieve[2] = true;
        sieve[3] = true;
        for (int x = 1; x <= limitSqrt; x++) {
            for (int y = 1; y <= limitSqrt; y++) {
                int n = (4 * x * x) + (y * y);
                if (n <= myLimit && (n % 12 == 1 || n % 12 == 5)) {
                    sieve[n] = !sieve[n];
                }
                n = (3 * x * x) + (y * y);
                if (n <= myLimit && (n % 12 == 7)) {
                    sieve[n] = !sieve[n];
                }
                n = (3 * x * x) - (y * y);
                if (x > y && n <= myLimit && (n % 12 == 11)) {
                    sieve[n] = !sieve[n];
                }
            }
        }
        for (int n = 5; n <= limitSqrt; n++) {
            if (sieve[n]) {
                int x = n * n;
                for (int i = x; i <= limit; i += x) {
                    sieve[i] = false;
                }
            }
        }
        int size = 0;
        for (int i = 0; i < limit; i++) {
            if (sieve[i]) size++;
        }
        int[] ress = new int[size];
        int i = 0;
        for (int j = 0; j < limit; j++) {
            if (sieve[j]) ress[i++] = j;
        }
        return ress;
    }
}
