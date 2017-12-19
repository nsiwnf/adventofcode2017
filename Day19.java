
import java.util.Arrays;
import java.util.List;

public class Day19 {

  static class Point {
    int x;

    int y;

    char direction; // D, U, L, R

    String result;

    public Point(int x, int y, char direction, String result) {
      this.x = x;
      this.y = y;
      this.result = result;
      this.direction = direction;
    }
  }

  static int part1(List<String> rows) {
    char[][] grid = new char[rows.size()][rows.iterator().next().length()];
    for (int i = 0; i < rows.size(); i++) {
      String row = rows.get(i);
      if (row.length() < rows.size()) {
        grid[i] = new char[rows.size()];
        Arrays.fill(grid[i], ' ');
        System.arraycopy(row.toCharArray(), 0, grid[i], 0, row.length());
      }
      else {
        grid[i] = row.toCharArray();
      }
    }

    Point p = null;
    for (int i = 0; i < grid[0].length; i++) {
      if (grid[0][i] == '|') {
        p = new Point(0, i, 'D', "");
        break;
      }
    }

    int steps = 0;
    while (p != null) {
      if (p.result.length() == 10) {
        System.out.println(p.result);
//         return p.result;
        return steps;
      }

      steps++;

      if (grid[p.x][p.y] >= 'A' && grid[p.x][p.y] <= 'Z') {
        p.result += grid[p.x][p.y];
      }

      if (grid[p.x][p.y] == '+') {
        // change direction
        if (p.direction == 'D' || p.direction == 'U') {
          if (p.y > 0 && grid[p.x][p.y - 1] == '-') {
            p.y--;
            p.direction = 'L';
          }
          else if (p.y < grid[p.x].length - 1 && grid[p.x][p.y + 1] == '-') {
            p.y++;
            p.direction = 'R';
          }
        }
        else if (p.direction == 'L' || p.direction == 'R') {
          if (p.x > 0 && grid[p.x - 1][p.y] == '|') {
            p.x--;
            p.direction = 'U';
          }
          else if (p.x < grid.length - 1 && grid[p.x + 1][p.y] == '|') {
            p.x++;
            p.direction = 'D';
          }
        }
      }
      // keep going
      else if (p.direction == 'D') {
        p.x++;
      }
      else if (p.direction == 'U') {
        p.x--;
      }
      else if (p.direction == 'L') {
        p.y--;
      }
      else if (p.direction == 'R') {
        p.y++;
      }
      else {
        p = null;
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    List<String> input = Util.readInput("day19.input");

    System.out.println(part1(input)); // 17302
  }
}
