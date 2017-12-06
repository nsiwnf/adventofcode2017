import java.util.Arrays;

/**
 * --- Day 3: Spiral Memory ---
 * 
 * You come across an experimental new kind of memory stored on an infinite
 * two-dimensional grid.
 * 
 * Each square on the grid is allocated in a spiral pattern starting at a
 * location marked 1 and then counting up while spiraling outward. For example,
 * the first few squares are allocated like this:
 * 
 *  17  16  15  14  13
 *  18   5   4   3  12
 *  19   6   1   2  11
 *  20   7   8   9  10
 *  21  22  23---> ...
 *
 * While this is very space-efficient (no squares are skipped), requested data must be
 * carried back to square 1 (the location of the only access port for this
 * memory system) by programs that can only move up, down, left, or right. They
 * always take the shortest path: the Manhattan Distance between the location of
 * the data and square 1.
 * 
 * For example:
 * 
 * Data from square 1 is carried 0 steps, since it's at the access port.
 * Data from square 12 is carried 3 steps, such as: down, left, left.
 * Data from square 23 is carried only 2 steps: up twice.
 * Data from square 1024 must be carried 31 steps.
 */
public class Day3 {

  public static int part1(int n) {
    // closes AxA location to n in grid
    int r = (int) Math.ceil(Math.sqrt(n));
    int nextFull = r * r;
    int distToOne = r - 1;

    if (nextFull == n) {
      return distToOne;
    }

    // Will be distToOne or distToOne + 1
    int halfway = nextFull - distToOne;
    int halfwayDistToOne = distToOne + (r + 1) % 2;

    if (halfway == n) {
      return halfwayDistToOne;
    }

    if (halfway > n) {
      // bump to same row as square
      int diff = halfway - n;
      n = halfway + diff;
    }

    // Same column as square
    int i = 0;
    while (halfway + i != n && nextFull - i != n) {
      i++;
    }
    return halfwayDistToOne - i;
  }

  /**
   * --- Part Two ---
   * 
   * As a stress test on the system, the programs here clear the grid and then
   * store the value 1 in square 1. Then, in the same allocation order as shown
   * above, they store the sum of the values in all adjacent squares, including
   * diagonals. <br>
   * So, the first few squares' values are chosen as follows: <br>
   * Square 1 starts with the value 1. <br>
   * Square 2 has only one adjacent filled square (with value 1), so it also stores 1. <br>
   * Square 3 has both of the above squares as neighbors and stores the sum of their values, 2. <br>
   * Square 4 has all three of the aforementioned squares as neighbors and stores the sum of their values, 4. <br>
   * Square 5 only has the first and fourth squares as neighbors, so it gets the value 5. <br>
   *
   * Once a square is written, its value does not change. Therefore, the first
   * few squares would receive the following values: <br>
   *
   * 147  142  133  122   59
   * 304    5    4    2   57
   * 330   10    1    1   54
   * 351   11   23   25   26
   * 362  747  806--->   ...
   * 
   */
  public static long part2(int n) {
    // base case
    if(n < 3) {
      return 1;
    }

    long[] grid = new long[n];
    Arrays.fill(grid, -1);

    // base case
    grid[0] = 1;
    grid[1] = 1;

    long sum = gridSum(n, grid);
    System.out.println(n + " : " + sum);
    return sum;
  }


  private static long gridSum(int n, long[] grid) {
    if (grid[n-1] != -1) {
      return grid[n-1];
    }

    // Always need this
    grid[n-1] = gridSum(n - 1, grid);

    // closes AxA location to n in grid
    int r = (int) Math.ceil(Math.sqrt(n));
    int closestSquareToN = r * r;

    // Corner
    if (n == closestSquareToN) {
      int diagPos = n == 4 ? 1 : (r - 2) * (r - 2);
      grid[n-1] += gridSum(diagPos, grid);
      grid[n-1] += gridSum(diagPos + 1, grid);
      return grid[n-1];
    }

    int halfwayDiagDistance = (r * r - (r - 2) * (r - 2) - 2);

    // Adj square corner
    int prevSquare = (r - 1) * (r - 1);
    if(n == prevSquare + 1) {
      grid[n-1] += gridSum(n - halfwayDiagDistance + 2, grid);
      return grid[n-1];
    }

    // Adj square corner
    if(n == prevSquare + 2) {
      int adjPos = n - halfwayDiagDistance + 1;
      grid[n-1] += gridSum(prevSquare, grid);
      grid[n-1] += gridSum(adjPos, grid);
      if(n > 6) {
        grid[n-1] += gridSum(adjPos + 1, grid);
      }
      return grid[n-1];
    }

    // Corner
    int halfway = closestSquareToN - (r - 1);
    int diagPos = halfway - halfwayDiagDistance;
    if (n == halfway) {
      grid[n-1] += gridSum(diagPos, grid);
      return grid[n-1];
    }

    // Adj halfway corner
    if(n == halfway + 1) {
      grid[n-1] += gridSum(diagPos, grid);
      grid[n-1] += gridSum(diagPos + 1, grid);
      grid[n-1] += gridSum(n - 2, grid);
      return grid[n-1];
    }

    // Adj halfway corner
    if(n == halfway - 1) {
      grid[n-1] += gridSum(diagPos, grid);
      grid[n-1] += gridSum(diagPos - 1, grid);
      return grid[n-1];
    }

    // Middle of an edge
    int adj = halfwayDiagDistance;
    if (n < halfway) {
      adj--;
    } else {
      adj++;
    }
    int adjN = n - adj;
    grid[n - 1] += gridSum(adjN, grid);
    grid[n - 1] += gridSum(adjN + 1, grid);
    grid[n - 1] += gridSum(adjN - 1, grid);

    return grid[n-1];
  }
}
