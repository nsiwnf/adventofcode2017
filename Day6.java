import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Day6 {

  public static int part1(int[] blocks) {
    List<Integer> iterations = redistributeBlocks(blocks.clone());
    return iterations.size() - 1;
  }

  public static int part2(int[] blocks) {
    List<Integer> iterations = redistributeBlocks(blocks.clone());
    int repeat = iterations.remove(iterations.size() - 1);
    return iterations.size() - iterations.indexOf(repeat);
  }

  private static List<Integer> redistributeBlocks(int[] blocks) {
    List<Integer> iterations = new ArrayList<>();
    int hashCode = Arrays.hashCode(blocks);
    while (!iterations.contains(hashCode)) {

      // find max
      int max = -1;
      int idx = 0;
      for (int i = 0; i < blocks.length; i++) {
        if (blocks[i] > max) {
          max = blocks[i];
          idx = i;
        }
      }

      // redistribute
      blocks[idx] = 0;
      while (max-- > 0) {
        idx = (idx + 1) % blocks.length;
        blocks[idx]++;
      }

      // log iteration
      iterations.add(hashCode);
      hashCode = Arrays.hashCode(blocks);
    }

    iterations.add(hashCode);
    return iterations;
  }

}
