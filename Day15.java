

public class Day15 {

  /**
   * The generators both work on the same principle. To create its next value, a
   * generator will take the previous value it produced, multiply it by a factor
   * (generator A uses 16807; generator B uses 48271), and then keep the
   * remainder of dividing that resulting product by 2147483647. That final
   * remainder is the value it produces next.
   * 
   * @return
   */
  static long part1(long initA, long initB) {
    int count = 0;
    long a = initA;
    long b = initB;
    for (int i = 0; i < 40000000; i++) {
      a = (a * 16807) % 2147483647;
      b = (b * 48271) % 2147483647;

      if ((a & 0xffff) == (b & 0xffff)) {
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
        a = (a * 16807) % 2147483647;
      }
      else if (!bValid) {
        b = (b * 48271) % 2147483647;
      }
      else {
        i++;
        if ((a & 0xffff) == (b & 0xffff)) {
          count++;
        }
        a = (a * 16807) % 2147483647;
        b = (b * 48271) % 2147483647;
      }
    }
    return count;
  }

  public static void main(String[] args) {
    // System.out.println(part2(65, 8921));
    System.out.println(part2(722, 354));
  }
}
