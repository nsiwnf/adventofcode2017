package com.adventofcode.advent2017;

import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class Day14 {
  static int part1(String input) {
    int[] stringOrig = new int[256];
    for (int i = 0; i < 256; i++) {
      stringOrig[i] = i;
    }
    int[] string = Arrays.copyOf(stringOrig, 256);

    byte[][] grid = new byte[128][128];

    int count = 0;
    for (int i = 0; i < 128; i++) {
      count += part1((input + '-' + i).getBytes(), string, grid[i]);
      System.arraycopy(stringOrig, 0, string, 0, 256);
    }

//     return count; // Part 1

    return part2(grid);
  }


  static class Point {
    int x;

    int y;

    public Point(int x, int y) {
      this.x = x;
      this.y = y;
    }
  }

  static int part2(byte[][] grid) {
    Queue<Point> pointQueue = new ArrayBlockingQueue<>(1000);

    int regions = 0;
    while (addNextPoint(grid, pointQueue)) {
      regions++;

      while (!pointQueue.isEmpty()) {
        Point p = pointQueue.poll();

        int x = p.x;
        int y = p.y;

        // mark visited
        if (grid[x][y] == 1) {
          grid[x][y] = 2;

          if (x < 127 && grid[x + 1][y] == 1) {
            pointQueue.add(new Point(x + 1, y));
          }

          if (x > 0 && grid[x - 1][y] == 1) {
            pointQueue.add(new Point(x - 1, y));
          }

          if (y < 127 && grid[x][y + 1] == 1) {
            pointQueue.add(new Point(x, y + 1));
          }

          if (y > 0 && grid[x][y - 1] == 1) {
            pointQueue.add(new Point(x, y - 1));
          }
        }
      }
    }

    return regions;
  }

  private static boolean addNextPoint(byte[][] grid, Queue<Point> pointQueue) {
    for (int x = 0; x < 128; x++) {
      for (int y = 0; y < 128; y++) {
        if (grid[x][y] == 1) {
          pointQueue.add(new Point(x, y));
          return true;
        }
      }
    }
    return false;
  }


  static int part1(byte[] input1, int[] string, byte[] row) {
    byte[] input = new byte[input1.length + 5];
    byte[] suffix = {17, 31, 73, 47, 23};
    System.arraycopy(input1, 0, input, 0, input1.length);
    System.arraycopy(suffix, 0, input, input1.length, suffix.length);

    int index = 0;
    int skipSize = 0;
    for (int r = 0; r < 64; r++) {
      for (int length : input) {
        for (int i = 0; i < length / 2; i++) {
          int n = (i + index) % string.length;
          int m = (length - i - 1 + index) % string.length;

          int b = string[m];
          string[m] = string[n];
          string[n] = b;
        }
        index = (index + length + skipSize) % string.length;
        skipSize++;
      }
    }

    int count = 0;
    byte[] hashes = new byte[16];
    for (int hashidx = 0; hashidx < 16; hashidx++) {
      for (int i = 0; i < 16; i++) {
        int a = string[16 * hashidx + i];
        hashes[hashidx] ^= a;
      }

      for (int i = 0; i < 8; i++) {
        row[8 * hashidx + 7 - i] = (byte) ((hashes[hashidx] >> i) & 1);
        count += row[8 * hashidx + 7 - i];
      }
    }

    return count;
  }


  public static void main(String[] args) {
    String input = "ffayrhll";
//    input = "flqrgnkx";
    System.out.println(part1(input));
  }
}
