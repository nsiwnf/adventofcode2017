
import java.util.ArrayList;
import java.util.List;

public class Day17 {

  static int part1(int input) {
    List<Integer> list = new ArrayList<>(2018);
    list.add(0);
    list.add(1);

    int index = 1;
    for (int i = 2; i < 2018; i++) {
      index = (index + 1 + input) % list.size();
      list.add(index, i);
    }
    return list.get(list.indexOf(2017) + 1);
  }

  static int part2(int input) {
    int index = 0;
    int indexOfZero = 0;
    int result = -1;
    for (int i = 1; i < 50000000; i++) {
      index = (index + 1 + input) % (i);
      if (index < indexOfZero) {
        indexOfZero++;
      }
      else if (index == indexOfZero) {
        result = i;
      }
    }

    return result;
  }

  public static void main(String[] args) {
    int input = 377;

    // System.out.println(part1(input));
    System.out.println(part2(377));
  }
}
