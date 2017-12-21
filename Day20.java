
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day20 {

  static class Particle {
    long[] pos;

    int[] vel;

    int[] acc;

    public Particle(int[] pos, int[] vel, int[] acc) {
      this.pos = new long[] { pos[0], pos[1], pos[2] };
      this.vel = vel;
      this.acc = acc;
    }

    public int getDistance() {
      long ldist = Math.abs(pos[0]) + Math.abs(pos[1]) + Math.abs(pos[2]);
      return ldist > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) ldist;
    }

    public int update() {
      vel[0] += acc[0];
      vel[1] += acc[1];
      vel[2] += acc[2];

      pos[0] += vel[0];
      pos[1] += vel[1];
      pos[2] += vel[2];

      long ldist = Math.abs(pos[0]) + Math.abs(pos[1]) + Math.abs(pos[2]);
      return ldist > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int) ldist;
    }

    public String printPos() {
      return Arrays.toString(pos);
    }

    @Override
    public String toString() {
      return Arrays.toString(pos) + "=" + getDistance();
    }
  }

  static int part1(List<String> input) {
    List<Particle> particles = new ArrayList<>();
    for (String particle : input) {
      int[] p = Arrays.stream(particle.replaceAll("p=<|v=<|a=<|>", "").split(",\\s*")).mapToInt(Integer::parseInt)
          .toArray();
      particles
          .add(new Particle(Arrays.copyOfRange(p, 0, 3), Arrays.copyOfRange(p, 3, 6), Arrays.copyOfRange(p, 6, 9)));
    }

    int minDist = Integer.MAX_VALUE;
    int particleIdx = 0;

    for (int i1 = 0; i1 < particles.size(); i1++) {
      Particle p = particles.get(i1);
      for (int i = 0; i < 10000; i++) {
        p.update();
      }
      if (minDist > p.getDistance()) {
        minDist = p.getDistance();
        particleIdx = i1;
      }
    }

    return particleIdx;
  }

  static int part2(List<String> input) {
    List<Particle> particles = new ArrayList<>();
    for (String particle : input) {
      int[] p = Arrays.stream(particle.replaceAll("p=<|v=<|a=<|>", "").split(",\\s*")).mapToInt(Integer::parseInt)
          .toArray();
      particles
          .add(new Particle(Arrays.copyOfRange(p, 0, 3), Arrays.copyOfRange(p, 3, 6), Arrays.copyOfRange(p, 6, 9)));
    }

    Map<String, Particle> collisions = new HashMap<>();
    List<Particle> toRemove = new ArrayList<>();
    for (int i = 0; i < 10000; i++) {
      int i1 = 0;
      while (i1 < particles.size()) {
        Particle p = particles.get(i1);
        Particle collided = collisions.computeIfAbsent(p.printPos(), k -> p);
        if(collided != p) {
          toRemove.add(p);
          toRemove.add(collided);
        }
        p.update();
        i1++;
      }
      particles.removeAll(toRemove);
      toRemove.clear();
      collisions.clear();
    }

    return particles.size();
  }

  public static void main(String[] args) {
    List<String> input = Util.readInput("day20.input");

    System.out.println(part2(input));
  }
}
