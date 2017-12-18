
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day7 {
  private static long WT_MASK = 0x0000ffff;

  private static long CT_MASK = 0xff00000000L;

  private static long ONE = 0x0100000000L; // 1 << 32

  static class Disc {
    String name;

    int wt;

    List<Disc> children;

    Disc parent;

    Disc(String name, int wt, List<Disc> children, Disc parent) {
      this.name = name;
      this.wt = wt;
      this.children = children;
      this.parent = parent;
    }

    @Override
    public String toString() {
      return name + " (" + wt + ")";
    }
  }

  public static int correctWeight(String input) {
    // Create map
    String[] rows = input.split("\n");
    Map<String, Disc> discs = new HashMap<>(rows.length);
    for (String row : rows) {
      String[] mapping = row.split(" -> ");
      int weight = Integer.parseInt(mapping[0].substring(mapping[0].indexOf('(') + 1, mapping[0].length() - 1));
      String name = mapping[0].substring(0, mapping[0].indexOf(' '));
      Disc p = discs.computeIfAbsent(name, n -> new Disc(n, weight, null, null));
      p.wt = weight;

      // Create/find discs for children
      if (mapping.length > 1) {
        String[] childNames = mapping[1].split(", ");
        List<Disc> children = new ArrayList<>(childNames.length);
        for (String child : childNames) {
          Disc d = discs.computeIfAbsent(child, k -> new Disc(child, -1, null, p));
          d.parent = p;
          children.add(d);

          // Prune map
          if (d.wt != -1) {
            discs.remove(child);
          }
        }
        p.children = children;
      }

      // Prune map
      if (p.parent != null) {
        discs.remove(name);
      }
    }

    // Should always be last left in map
    Disc root = discs.values().iterator().next();
//    return getCorrectWeight(root);
    return getCorrectWeightCleaner(root);
  }

  static class DiscInfo {
    int count;

    int discWt;

    int totWt;

    DiscInfo(int count, int discWt, int totWt) {
      this.count = count;
      this.discWt = discWt;
      this.totWt = totWt;
    }
  }

  private static int getCorrectWeightCleaner(Disc root) {
    if (root.children != null) {
      DiscInfo result1 = new DiscInfo(0, 0, 0);
      DiscInfo result2 = new DiscInfo(0, 0, 0);
      for (Disc child : root.children) {
        int childWeight = child.wt;

        // also updates cumulative child weights
        int correctWt = getCorrectWeightCleaner(child);
        if (correctWt != 0) {
          return correctWt;
        }

        // update count | individual wt | total wt
        if (result1.count == 0 || result1.totWt == child.wt) {
          result1.count++;
          result1.discWt = childWeight;
          result1.totWt = child.wt;
        }
        else if (result2.count == 0 || result2.totWt == child.wt) {
          result2.count++;
          result2.discWt = childWeight;
          result2.totWt = child.wt;
        }

        // Update cumulative wt
        root.wt += child.wt;
      }

      if (result2.count != 0) {
        int diff = result2.totWt - result1.totWt;

        // count==1 is the incorrect value
        return result1.count == 1 ? result1.discWt + diff : result2.discWt - diff;
      }
    }

    return 0;
  }

  private static int getCorrectWeight(Disc root) {
    if (root.children != null) {
      // Store "count" at 0xFF00000000, wt at 0xFFFF0000 and total at 0x0000FFFF
      long result1 = 0;
      long result2 = 0;
      for (Disc child : root.children) {
        int childWeight = child.wt;

        // also updates cumulative child weights
        int correctWt = getCorrectWeight(child);
        if (correctWt != 0) {
          return correctWt;
        }

        // update count | individual wt | cumulative wt
        if (result1 == 0 || (result1 & WT_MASK) == child.wt) {
          result1 = ((result1 + ONE) & CT_MASK) | childWeight << 16 | child.wt;
        }
        else if (result2 == 0 || (result2 & WT_MASK) == child.wt) {
          result2 = ((result2 + ONE) & CT_MASK) | childWeight << 16 | child.wt;
        }

        // Update cumulative wt
        root.wt += child.wt;
      }

      // Found imbalance - result with count == 1 is the incorrect one
      if (result2 != 0) {
        long diff = (result2 & WT_MASK) - (result1 & WT_MASK);
        return (int) ((result1 & CT_MASK) == ONE ? ((result1 >> 16) & WT_MASK) + diff : ((result2 >> 16) & WT_MASK) - diff);
      }
    }

    return 0;
  }

  private static String decodeTopDisc(int encoded) {
    return "Total=" + (encoded & 0xFFFF) +
        ", Weight=" + ((encoded >> 16) & 0xFF) +
        ", Count=" + (encoded >> 24);
  }

}
