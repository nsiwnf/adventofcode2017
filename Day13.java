public class Day13 {

  static int part1(int[] depths, int[] range) {
    int[] scanner = new int[depths.length];

    int severity = 0;
    int i = 0;
    int time = 0;
    while (i < depths.length) {
      if (time == depths[i]) {
        if (scanner[i] == 0) {
          severity += depths[i] * range[i];
        }
        i++;
      }

      // update scanner positions
      for (int j = 0; j < scanner.length; j++) {
        if (scanner[j] == range[j] - 1) {
          scanner[j] *= -1;
        }
        scanner[j]++;
      }

      time++;
    }

    return severity;
  }

  static long part2(int[] depths, int[] range) {
    long i = 0;
    while (!part2(i, depths, range)) {
      i++;
    }
    return i;
  }

  static boolean part2(long offset, int[] depths, int[] range) {
    int i = 0;
    long time = offset;
    while (i < depths.length) {
      if (time == depths[i] + offset) {
        if (time % (2 * range[i] - 2) == 0) {
          return false;
        }
        i++;
      }

      time++;
    }

    return true;
  }

  public static void main(String[] args) {
    int[] depths = { 0, 1, 2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 46, 48,
        50, 52, 54, 58, 60, 62, 64, 66, 68, 72, 74, 76, 86, 88, 92, 94, 96, 98 };
    int[] range = { 4, 2, 3, 4, 8, 5, 6, 6, 10, 8, 6, 9, 8, 6, 8, 8, 12, 12, 12, 12, 10, 12, 12, 14, 8, 14, 12, 14, 14,
        14, 12, 14, 14, 12, 12, 14, 18, 17, 14, 20, 14, 14, 18, 18 };

    // depths = new int[] { 0, 1, 4, 6 };
    // range = new int[] { 3, 2, 4, 4 };

    System.out.println(part1(depths, range));
    System.out.println(part2(depths, range));
  }
}
