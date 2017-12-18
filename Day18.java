import java.util.List;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Day18 {

  private static Queue<Long> queueA = new ArrayBlockingQueue<Long>(10000);

  private static Queue<Long> queueB = new ArrayBlockingQueue<Long>(10000);

  static long part1(long[] registers, List<String> instructions) {
    long lastFreq = -1;
    int i = 0;
    while (i < instructions.size()) {
      String[] instruction = instructions.get(i).split(" ");
      String command = instruction[0];
      int x = instruction[1].charAt(0);
      if (command.equals("set")) {
        registers[x - 'a'] = getY(registers, instruction[2]);
      }
      else if (command.equals("add")) {
        registers[x - 'a'] += getY(registers, instruction[2]);
      }
      else if (command.equals("mul")) {
        registers[x - 'a'] *= getY(registers, instruction[2]);
      }
      else if (command.equals("mod")) {
        registers[x - 'a'] %= getY(registers, instruction[2]);
      }
      else if (command.equals("snd")) {
        lastFreq = registers[x - 'a'];
      }
      else if (command.equals("rcv")) {
        if (registers[x - 'a'] != 0) {
          return lastFreq;
        }
      }
      else if (x > '0' && x <= '9' || x >= 'a' && x <= 'z' && registers[x - 'a'] > 0) { // jgz
        long jump = getY(registers, instruction[2]) - 1;
        i += jump;
      }

      i++;
    }

    return lastFreq;
  }

  static long part2(long[] registers1, long[] registers2, List<String> instructions) {
    int i1 = 0;
    int i2 = 0;
    int prev1 = -1;
    int prev2 = -1;
    while ((i1 < instructions.size() || i2 < instructions.size()) && (prev1 != i1 || prev2 != i2)) {
      prev1 = i1;
      prev2 = i2;
      i1 = execute(instructions, i1, registers1, queueA, queueB);
      i2 = execute(instructions, i2, registers2, queueB, queueA);
    }

    return count;
  }

  static int count = 0;

  static int execute(List<String> instructions, int i, long[] registers, Queue<Long> recv, Queue<Long> send) {
    if (i == -1 || i >= instructions.size()) {
      return -1;
    }
    String[] instruction = instructions.get(i).split(" ");
    String command = instruction[0];
    int x = instruction[1].charAt(0);
    if (command.equals("set")) {
      registers[x - 'a'] = getY(registers, instruction[2]);
    }
    else if (command.equals("add")) {
      registers[x - 'a'] += getY(registers, instruction[2]);
    }
    else if (command.equals("mul")) {
      registers[x - 'a'] *= getY(registers, instruction[2]);
    }
    else if (command.equals("mod")) {
      registers[x - 'a'] %= getY(registers, instruction[2]);
    }
    else if (command.equals("snd")) {
      if (send == queueA) {
        count++;
      }
      send.add(registers[x - 'a']);
    }
    else if (command.equals("rcv")) {
      if (!recv.isEmpty()) {
        registers[x - 'a'] = recv.poll();
      }
      else {
        i--;
      }
    }
    else if (x > '0' && x <= '9' || x >= 'a' && x <= 'z' && registers[x - 'a'] > 0) { // jgz
      long jump = getY(registers, instruction[2]) - 1;
      i += jump;
    }

    return i + 1;
  }

  static long getY(long[] registers, String yStr) {
    char y = yStr.charAt(0);
    if (y >= '0' && y <= '9' || y == '-') {
      return Integer.parseInt(yStr);
    }
    else {
      return registers[y - 'a'];
    }
  }

  public static void main(String[] args) {
    List<String> input = Util.readInput("day18.input");
    long[] registers2 = new long[26];
    registers2['p' - 'a'] = 1;
    System.out.println(part2(new long[26], registers2, input));
  }
}
