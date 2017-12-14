package com.adventofcode.advent2017;

public class Day11 {
  
  static int part1(String input) {
    int sw = 0;
    int s = 0;
    int se = 0;
    int max = 0;
    String[] rows = input.split(",");
    for (String row : rows) {
      switch (row) {
        case "se":
          se++;
          break;
        case "sw":
          sw++;
          break;
        case "s":
          s++;
          break;
        case "ne":
          sw--;
          break;
        case "nw":
          se--;
          break;
        case "n":
          s--;
          break;
      }

      // calculate distance
      max = Math.max(max, calcDist(s, sw, se));
    }

    int dist = calcDist(s, sw, se);

    System.out.println("shortest=" + dist);
    System.out.println("max=" + max);
    return 0;
  }

  static int calcDist(int s, int sw, int se) {
    int se1 = Math.abs(se);
    int s1 = Math.abs(s);
    int sw1 = Math.abs(sw);
    int sub = 0;

    if (s > 0 && sw > 0 && se > 0 ||
        s > 0 && sw < 0 && se < 0 ||
        s < 0 && sw > 0 && se > 0 ||
        s < 0 && sw < 0 && se < 0) {
      sub = Math.min(se1, sw1);
    } else if (s > 0 && sw < 0 && se > 0 ||
        s < 0 && sw < 0 && se > 0) {
      sub = Math.min(s1, sw1);
    } else if (s > 0 && sw > 0 && se < 0 ||
        s < 0 && sw < 0 && se < 0) {
      sub = Math.min(s1, se1);
    }

    return s1 + se1 + sw1 - sub;
  }
}
