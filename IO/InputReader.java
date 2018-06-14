package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class InputReader {
    BufferedReader reader;
    StringTokenizer tokenizer;

    public InputReader(InputStream stream) {
        reader = new BufferedReader(new InputStreamReader(stream), 32768);
        tokenizer = null;
    }

    public String next() {
        while (tokenizer == null || !tokenizer.hasMoreTokens()) {
            try {
                tokenizer = new StringTokenizer(reader.readLine(), " \t\n\r\f,");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return tokenizer.nextToken();
    }

    public int NextInt() {
        return Integer.parseInt(next());
    }

    public long NextLong() {
        return Long.parseLong(next());
    }

    public double NextDouble() {
        return Double.parseDouble(next());
    }

    public int[] NextIntArray(int n) {
        return NextIntArray(n, 0);
    }

    public int[] NextIntArray(int n, int offset) {
        int[] a = new int[n];
        for (int i = 0; i < n; i++) {
            a[i] = NextInt() + offset;
        }
        return a;
    }

    public int[][] NextIntMatrix(int n, int m) {
        return NextIntMatrix(n, m, 0);
    }

    public int[][] NextIntMatrix(int n, int m, int offset) {
        int[][] a = new int[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = NextInt() + offset;
            }
        }
        return a;
    }


    public long[] NextLongArray(int n) {
        return NextLongArray(n, 0);
    }

    public long[] NextLongArray(int n, int offset) {
        long[] a = new long[n];
        for (int i = 0; i < n; i++) {
            a[i] = NextLong() + offset;
        }
        return a;
    }

    public long[][] NextLongMatrix(int n, int m) {
        return NextLongMatrix(n, m, 0);
    }

    public long[][] NextLongMatrix(int n, int m, int offset) {
        long[][] a = new long[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                a[i][j] = NextLong() + offset;
            }
        }
        return a;
    }
}