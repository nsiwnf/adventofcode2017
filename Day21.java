
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 {

  static int part1(Map<String, String> rules, char[][] grid) {
    StringBuilder b = new StringBuilder();
    int newSize = grid.length;
    int size = 0;
    for (int i = 0; i < 18; i++) {
      if (grid.length % 2 == 0) {
        newSize = grid.length / 2 * 3;
        size = 2;
      }
      else if (grid.length % 3 == 0) {
        newSize = grid.length / 3 * 4;
        size = 3;
      }

      char[][] newGrid = new char[newSize][newSize];

      int x1 = 0;
      for (int x = 0; x < grid.length; x += size) {
        int y1 = 0;
        for (int y = 0; y < grid.length; y += size) {
          b.setLength(0);
          if (size == 3) {
            b.append(grid[x][y]).append(grid[x][y + 1]).append(grid[x][y + 2]).append('/');
            b.append(grid[x + 1][y]).append(grid[x + 1][y + 1]).append(grid[x + 1][y + 2]).append('/');
            b.append(grid[x + 2][y]).append(grid[x + 2][y + 1]).append(grid[x + 2][y + 2]);
          }
          else if (size == 2) {
            b.append(grid[x][y]).append(grid[x][y + 1]).append('/');
            b.append(grid[x + 1][y]).append(grid[x + 1][y + 1]);
          }

          String rule = rules.get(b.toString());
          char[] ruleChars = rule.toCharArray();
          if (size == 3) {
            newGrid[x1][y1] = ruleChars[0];
            newGrid[x1 + 1][y1] = ruleChars[1];
            newGrid[x1 + 2][y1] = ruleChars[2];
            newGrid[x1 + 3][y1] = ruleChars[3];
            newGrid[x1][y1 + 1] = ruleChars[5];
            newGrid[x1 + 1][y1 + 1] = ruleChars[6];
            newGrid[x1 + 2][y1 + 1] = ruleChars[7];
            newGrid[x1 + 3][y1 + 1] = ruleChars[8];
            newGrid[x1][y1 + 2] = ruleChars[10];
            newGrid[x1 + 1][y1 + 2] = ruleChars[11];
            newGrid[x1 + 2][y1 + 2] = ruleChars[12];
            newGrid[x1 + 3][y1 + 2] = ruleChars[13];
            newGrid[x1][y1 + 3] = ruleChars[15];
            newGrid[x1 + 1][y1 + 3] = ruleChars[16];
            newGrid[x1 + 2][y1 + 3] = ruleChars[17];
            newGrid[x1 + 3][y1 + 3] = ruleChars[18];
          }
          else if (size == 2) {
            newGrid[x1][y1] = ruleChars[0];
            newGrid[x1 + 1][y1] = ruleChars[1];
            newGrid[x1 + 2][y1] = ruleChars[2];
            newGrid[x1][y1 + 1] = ruleChars[4];
            newGrid[x1 + 1][y1 + 1] = ruleChars[5];
            newGrid[x1 + 2][y1 + 1] = ruleChars[6];
            newGrid[x1][y1 + 2] = ruleChars[8];
            newGrid[x1 + 1][y1 + 2] = ruleChars[9];
            newGrid[x1 + 2][y1 + 2] = ruleChars[10];
          }
          y1 += size + 1;
        }
        x1 += size + 1;
      }

      grid = newGrid;
    }

    int count = 0;
    for (int x = 0; x < grid.length; x++) {
      for (int y = 0; y < grid.length; y++) {
        if (grid[x][y] == '#') {
          count++;
        }
      }
    }
    return count;
  }

  static Map<String, String> convertRules(List<String> rows) {
    StringBuilder b = new StringBuilder();
    Map<String, String> rules = new HashMap<>(rows.size());
    for (String row : rows) {
      String[] rule = row.split(" => ");
      rules.put(rule[0], rule[1]);

      // ABC
      // DEF
      // GHI

      // ABC/DEF/GHI
      // IHG/FED/CBA
      // GHI/DEF/ABC
      // CBA/FED/IHG

      // ADG/BEH/CFI
      // IFC/HEB/GDA
      // GDA/HEB/IFC
      // CFI/BEH/ADG

      String[] keys = rule[0].split("/");
      if (keys.length == 3) {
        b.setLength(0);
        b.append(rule[0]).reverse();
        rules.put(b.toString(), rule[1]);
        b.setLength(0);
        b.append(keys[2]).append('/').append(keys[1]).append('/').append(keys[0]);
        rules.put(b.toString(), rule[1]);
        rules.put(b.reverse().toString(), rule[1]);
        b.setLength(0);
        b.append(keys[0].charAt(0)).append(keys[1].charAt(0)).append(keys[2].charAt(0)).append('/')
            .append(keys[0].charAt(1)).append(keys[1].charAt(1)).append(keys[2].charAt(1)).append('/')
            .append(keys[0].charAt(2)).append(keys[1].charAt(2)).append(keys[2].charAt(2));
        rules.put(b.toString(), rule[1]);
        rules.put(b.reverse().toString(), rule[1]);
        b.setLength(0);
        b.append(keys[2].charAt(0)).append(keys[1].charAt(0)).append(keys[0].charAt(0)).append('/')
            .append(keys[2].charAt(1)).append(keys[1].charAt(1)).append(keys[0].charAt(1)).append('/')
            .append(keys[2].charAt(2)).append(keys[1].charAt(2)).append(keys[0].charAt(2));
        rules.put(b.toString(), rule[1]);
        rules.put(b.reverse().toString(), rule[1]);
      }
      else {
        // AB
        // DE

        // AB/DE
        // ED/BA
        // AD/BE
        // EB/DA
        // DE/AB
        // BA/ED
        // DA/EB
        // BE/AD

        b.setLength(0);
        b.append(rule[0]).reverse();
        rules.put(b.toString(), rule[1]);
        b.setLength(0);
        b.append(keys[0].charAt(0)).append(keys[1].charAt(0)).append('/')
            .append(keys[0].charAt(1)).append(keys[1].charAt(1));
        rules.put(b.toString(), rule[1]);
        rules.put(b.reverse().toString(), rule[1]);
        b.setLength(0);
        b.append(keys[1]).append('/').append(keys[0]);
        rules.put(b.toString(), rule[1]);
        rules.put(b.reverse().toString(), rule[1]);
        b.setLength(0);
        b.append(keys[1].charAt(0)).append(keys[0].charAt(0)).append('/')
            .append(keys[1].charAt(1)).append(keys[0].charAt(1));
        rules.put(b.toString(), rule[1]);
        rules.put(b.reverse().toString(), rule[1]);
      }
    }
    return rules;
  }

  static String printGrid(char[][] grid) {
    StringBuilder b = new StringBuilder();
    for (char[] aGrid : grid) {
      b.append(aGrid).append('\n');
    }
    return b.toString();
  }

  public static void main(String[] args) {
    Map<String, String> input = convertRules(Util.readInput("day21.input"));
    char[][] grid = new char[][] {
        ".#.".toCharArray(),
        "..#".toCharArray(),
        "###".toCharArray()
    };

    Map<String, String> testInput = convertRules(Arrays.asList(
        "../.# => ##./#../...",
        ".#./..#/### => #..#/..../..../#..#"));

    System.out.println(part1(input, grid));
  }
}
