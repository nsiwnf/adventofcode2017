
/**
 * --- Day 2: Corruption Checksum ---
 * 
 * As you walk through the door, a glowing humanoid shape yells in your
 * direction. "You there! Your state appears to be idle. Come help us repair the
 * corruption in this spreadsheet - if we take another millisecond, we'll have
 * to display an hourglass cursor!"
 * 
 * The spreadsheet consists of rows of apparently-random numbers. To make sure
 * the recovery process is on the right track, they need you to calculate the
 * spreadsheet's checksum. For each row, determine the difference between the
 * largest value and the smallest value; the checksum is the sum of all of these
 * differences.
 * 
 * For example, given the following spreadsheet:
 *
 * 5 1 9 5
 * 7 5 3
 * 2 4 6 8
 * 
 * The first row's largest and smallest values are 9 and 1, and their difference is 8.
 * The second row's largest and smallest values are 7 and 3, and their difference is 4.
 * The third row's difference is 6.
 * In this example, the spreadsheet's checksum would be 8 + 4 + 6 = 18.
 */
public class Day2 {

  public static int part1(String input) {
    String[] rows = input.split("\n");
    int result = 0;
    for (String row : rows) {
      int max = -1;
      int min = Integer.MAX_VALUE;
      String[] numbers = row.split("\t");
      for (String number : numbers) {
        int n = Integer.parseInt(number);
        if (max < n) {
          max = n;
        }
        if (min > n) {
          min = n;
        }
      }
      result += (max - min);
    }

    return result;
  }

  /**
   * Evenly divisible
   * 
   * @param input
   * @return
   */
  public static int part2(String input) {
    String[] rows = input.split("\n");
    int result = 0;
    for (String row : rows) {
      String[] numbers = row.split("\t");
      result += addDivisibles(0, 1, numbers);
    }

    return result;
  }
  
  private static int addDivisibles(int i, int j, String[] numbers) {
    if (i < j && j < numbers.length) {
      int a = Integer.parseInt(numbers[i]);
      int b = Integer.parseInt(numbers[j]);

      int d = addDivisible(a, b);
      if (d == 0) {
        int x = addDivisibles(i + 1, j, numbers);
        if (x == 0)
          return addDivisibles(i, j + 1, numbers);
        else
          return x;
      }
      else
        return d;
    }
    else
      return 0;
  }

  private static int addDivisible(int n, int m) {
    if (n % m == 0) {
      return n / m;
    }
    else if (m % n == 0) {
      return m / n;
    }
    return 0;
  }
