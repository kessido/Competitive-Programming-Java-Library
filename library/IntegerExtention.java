package library;

public class IntegerExtention {
    public static int getNumberOfBits(int i) {
        return 32 - Integer.numberOfLeadingZeros(i);
    }

    public static int getNumberOfBits(long i) {
        return 64 - Long.numberOfLeadingZeros(i);
    }

    public static class Integer_RandomSequenceGenerator {
        int x = 8753, y = 239017, z = 1000000123;

        public Integer_RandomSequenceGenerator() {
        }

        public Integer_RandomSequenceGenerator(int x, int y, int z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public int nextInt() {
            int t = x ^ (x << 11);
            x = y;
            y = z;
            z ^= (z >> 19) ^ t ^ (t >> 8);
            return z;
        }
    }
}
