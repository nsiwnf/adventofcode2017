
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;
import java.util.concurrent.ArrayBlockingQueue;

public class Day22 {

  static class Point {
    int x;

    int y;

    int dist;

    char dir;

    Point(int x, int y, int dist, char dir) {
      this.x = x;
      this.y = y;
      this.dist = dist;
      this.dir = dir;
    }

    void turnRight() {
      if (dir == 'N') {
        dir = 'E';
      }
      else if (dir == 'E') {
        dir = 'S';
      }
      else if (dir == 'S') {
        dir = 'W';
      }
      else if (dir == 'W') {
        dir = 'N';
      }
    }

    void turnLeft() {
      if (dir == 'N') {
        dir = 'W';
      }
      else if (dir == 'W') {
        dir = 'S';
      }
      else if (dir == 'S') {
        dir = 'E';
      }
      else if (dir == 'E') {
        dir = 'N';
      }
    }

    void reverse() {
      if (dir == 'N') {
        dir = 'S';
      }
      else if (dir == 'E') {
        dir = 'W';
      }
      else if (dir == 'S') {
        dir = 'N';
      }
      else if (dir == 'W') {
        dir = 'E';
      }
    }

    void moveForward() {
      if (dir == 'N') {
        x--;
      }
      else if (dir == 'E') {
        y++;
      }
      else if (dir == 'S') {
        x++;
      }
      else if (dir == 'W') {
        y--;
      }
    }
  }

  static int part1(List<String> input) {
    int infected = 0;
    int[] tmp = new int[2];
    List<String> offGridInfected = new ArrayList<>();
    char[][] grid = createGrid(input);
    Point p = new Point(grid.length / 2, grid.length / 2, 0, 'N');

    while (p.dist < 10000) {
      p.dist++;

      // off the grid
      if (p.x >= grid.length || p.x < 0 || p.y >= grid.length || p.y < 0) {
        tmp[0] = p.x;
        tmp[1] = p.y;
        String offGrid = Arrays.toString(tmp);
        if (offGridInfected.remove(offGrid)) {
          p.turnRight();
        }
        else {
          offGridInfected.add(offGrid);
          p.turnLeft();
          infected++;
        }
      }
      else if (grid[p.x][p.y] == '#') {
        // turn right
        p.turnRight();
        // clean
        grid[p.x][p.y] = '.';
      }
      else if (grid[p.x][p.y] == '.') {
        // turn left
        p.turnLeft();
        // infect
        grid[p.x][p.y] = '#';
        infected++;
      }

      p.moveForward();
    }

    return infected;
  }

  static int part2(List<String> input) {
    int infected = 0;
    int[] tmp = new int[2];
    Map<String, Character> offGridState = new HashMap<>();
    char[][] grid = createGrid(input);
    Point p = new Point(grid.length / 2, grid.length / 2, 0, 'N');

    while (p.dist < 10000000) {
      p.dist++;

      // off the grid
      if (p.x >= grid.length || p.x < 0 || p.y >= grid.length || p.y < 0) {
        tmp[0] = p.x;
        tmp[1] = p.y;
        String offGrid = Arrays.toString(tmp);
        Character state = offGridState.remove(offGrid);
        if (state == null) {
          p.turnLeft();
          offGridState.put(offGrid, 'W');
        }
        else if (state.equals('W')) {
          offGridState.put(offGrid, '#');
          infected++;
        }
        else if (state.equals('#')) {
          p.turnRight();
          offGridState.put(offGrid, 'F');
        }
        else if (state.equals('F')) {
          p.reverse();
        }
      }
      else if (grid[p.x][p.y] == '.') {
        p.turnLeft();
        grid[p.x][p.y] = 'W';
      }
      else if (grid[p.x][p.y] == 'W') {
        grid[p.x][p.y] = '#';
        infected++;
      }
      else if (grid[p.x][p.y] == '#') {
        p.turnRight();
        grid[p.x][p.y] = 'F';
      }
      else if (grid[p.x][p.y] == 'F') {
        p.reverse();
        grid[p.x][p.y] = '.';
      }

      p.moveForward();
    }

    return infected;
  }

  private static char[][] createGrid(List<String> input) {
    char[][] grid = new char[input.size()][];
    for (int i = 0; i < input.size(); i++) {
      String row = input.get(i);
      grid[i] = row.toCharArray();
    }

    return grid;
  }

  public static void main(String[] args) {
    List<String> input = Util.readInput("day22.input");

//    input = Arrays.asList(
//        "..#",
//        "#..",
//        "...");

    // System.out.println(part1(input));
    long start = System.currentTimeMillis();
    System.out.println(part2(input));
    long end = System.currentTimeMillis();
    System.out.println(end - start);
  }
}
