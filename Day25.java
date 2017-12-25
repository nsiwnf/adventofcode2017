
import java.util.HashSet;
import java.util.Set;

public class Day25 {

  enum State {
    A,
    B,
    C,
    D,
    E,
    F
  }

  private static int part1(int input) {
    Set<Integer> oneIdx = new HashSet<>();
    State state = State.A;
    int currentIdx = 0;

    for (int i = 0; i < input; i++) {
      int currentValue = oneIdx.contains(currentIdx) ? 1 : 0;
      switch (state) {
      case A:
        if (currentValue == 0) {
          oneIdx.add(currentIdx);
          currentIdx++;
          state = State.B;
        }
        else {
          oneIdx.remove(currentIdx);
          currentIdx++;
          state = State.C;
        }

        break;
      case B:
        if (currentValue == 0) {
          oneIdx.remove(currentIdx);
          currentIdx--;
          state = State.A;
        }
        else {
          oneIdx.remove(currentIdx);
          currentIdx++;
          state = State.D;
        }

        break;
      case C:
        if (currentValue == 0) {
          oneIdx.add(currentIdx);
          currentIdx++;
          state = State.D;
        }
        else {
          oneIdx.add(currentIdx);
          currentIdx++;
          state = State.A;
        }
        break;
      case D:
        if (currentValue == 0) {
          oneIdx.add(currentIdx);
          currentIdx--;
          state = State.E;
        }
        else {
          oneIdx.remove(currentIdx);
          currentIdx--;
          state = State.D;
        }
        break;
      case E:
        if (currentValue == 0) {
          oneIdx.add(currentIdx);
          currentIdx++;
          state = State.F;
        }
        else {
          oneIdx.add(currentIdx);
          currentIdx--;
          state = State.B;
        }
        break;
      case F:
        if (currentValue == 0) {
          oneIdx.add(currentIdx);
          currentIdx++;
          state = State.A;
        }
        else {
          oneIdx.add(currentIdx);
          currentIdx++;
          state = State.E;
        }
        break;
      }
    }
    return oneIdx.size();
  }

  public static void main(String[] args) {
    int input = 12368930;
    System.out.println(part1(input));
  }
}
