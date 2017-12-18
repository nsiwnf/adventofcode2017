
import java.util.ArrayList;
import java.util.List;

public class Day16 {

  static class Command {
    char op;

    int a;

    int b;

    Command(char op, int a, int b) {
      this.op = op;
      this.a = a;
      this.b = b;
    }

    @Override
    public String toString() {
      return op + " " + a + " " + b;
    }
  }

  private static char[] _tmp = new char[16];

  static char[] part1(char[] dancers, List<Command> input) {
    int offset = 0;
    for (Command move : input) {
      char c = move.op;

      switch (c) {
      case 's':
        offset = move.a;
        break;
      case 'x':
        char x = dancers[move.a];
        dancers[move.a] = dancers[move.b];
        dancers[move.b] = x;
        break;
      case 'p': {
        char a = (char) move.a;
        char b = (char) move.b;

        int t = -1;
        int i = 0;
        while (i < 16 && t != -2) {
          if (dancers[i] == a || dancers[i] == b) {
            if (t == -1) {
              t = i;
            }
            else {
              char tmp = dancers[i];
              dancers[i] = dancers[t];
              dancers[t] = tmp;
              t = -2;
            }
          }
          i++;
        }
        break;
      }
      }
    }

    offset = offset % dancers.length;

    System.arraycopy(dancers, dancers.length - offset, _tmp, 0, offset);
    System.arraycopy(dancers, 0, dancers, offset, dancers.length - offset);
    System.arraycopy(_tmp, 0, dancers, 0, offset);

    return dancers;
  }

  static String part2(char[] dancers, String[] split) {
    List<Command> moves = convert(split, dancers.length);
    int init = 1;
    List<String> results = new ArrayList<>();
    results.add(new String(dancers));
    for (int i = init; i < 1000000000; i++) {
      dancers = part1(dancers, moves);
      if (results.contains(new String(dancers))) {
        return results.get(1000000000 % (i));
      }
      else {
        results.add(new String(dancers));
      }
    }
    return new String(dancers);
  }

  static List<Command> convert(String[] input, int nDancers) {
    List<Command> result = new ArrayList<>(input.length);

    int offset = 0;
    for (String move : input) {
      char c = move.charAt(0);
      if (c == 's') {
        offset = (offset + Integer.parseInt(move.substring(1))) % nDancers;
      }
      else if (c == 'x') {
        int idx = move.indexOf('/');
        int posA = (Integer.parseInt(move.substring(1, idx)) + nDancers - offset) % nDancers;
        int posB = (Integer.parseInt(move.substring(idx + 1)) + nDancers - offset) % nDancers;
        result.add(new Command('x', posA, posB));
      }
      else if (c == 'p') {
        int idx = move.indexOf('/');
        char a = move.charAt(1);
        char b = move.charAt(idx + 1);
        result.add(new Command('p', a, b));
      }
    }

    result.add(new Command('s', offset, -1));
    return result;
  }

  public static void main(String[] args) {
    List<String> input = Util.readInput("day16.input");
    String[] split = input.iterator().next().split(",");

    char[] dancers = getChars(16);
    // bkgcdefiholnpmja
    // System.out.println(part1(dancers, convert(split, dancers.length)));
    // knmdfoijcbpghlea
    System.out.println(part2(dancers, split));

  }

  private static char[] getChars(int n) {
    char[] dancers = new char[n];
    for (int c = 0; c < n; c++) {
      dancers[c] = (char) (c + 'a');
    }
    return dancers;
  }
}
