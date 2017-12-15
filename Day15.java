public class Day15 {
  private static final int A_MULT = 16807;

  private static final int B_MULT = 48271;

  private static final int DIV = 2147483647;

  static long part1(long initA, long initB) {
    int count = 0;
    long a = initA;
    long b = initB;
    for (int i = 0; i < 40000000; i++) {
      a = (a * A_MULT) % DIV;
      b = (b * B_MULT) % DIV;

      if ((short) a == (short) b) {
        count++;
      }

    }
    return count;
  }

  static long part2(long initA, long initB) {
    int count = 0;
    long a = initA;
    long b = initB;
    int i = 0;
    while (i < 5000000) {
      boolean aValid = a % 4 == 0;
      boolean bValid = b % 8 == 0;

      if (!aValid) {
        a = (a * A_MULT) % DIV;
      }
      else if (!bValid) {
        b = (b * B_MULT) % DIV;
      }
      else {
        i++;
        if ((a & 0xffff) == (b & 0xffff)) {
          count++;
        }
        a = (a * A_MULT) % DIV;
        b = (b * B_MULT) % DIV;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    // System.out.println(part2(65, 8921));
    System.out.println(part2(722, 354));
  }
}
