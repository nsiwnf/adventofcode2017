/**
 * --- Day 5: A Maze of Twisty Trampolines, All Alike ---
 */
public class Day5 {

  public static int part1(int[] maze) {
    int steps = 0;
    int i = 0;
    while (i < maze.length) {
      i = i + maze[i]++;
      steps++;
    }

    return steps;
  }

  public static int part2(int[] maze) {
    int steps = 0;
    int i = 0;
    while (i < maze.length) {
      int next = i + maze[i];
      maze[i] += (maze[i] >= 3 ? -1 : 1);
      i = next;
      steps++;
    }

    return steps;
  }
}
