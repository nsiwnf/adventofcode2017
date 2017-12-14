package com.adventofcode.advent2017;

import java.util.Stack;

public class Day9 {
  
  static int part1(String input) {
    int weight = 1;
    int score = 0;
    int canceled = 0;
    Stack<Character> stack = new Stack<>();
    int i = 0;
    stack.push(input.charAt(i++));

    while (i < input.length()) {
      char c = stack.peek();
      char n = input.charAt(i++);

      if (n == '!') {
        i++;
      } else if (c == '{') {
        if (n == '}') {
          // found closing
          stack.pop();
          score += weight;
          weight--;
        } else if (n == '{') {
          weight++;
          stack.push(n);
        } else if (n == '<') {
          stack.push('<');
        }
      } else if (c == '<') {
        if (n == '>') {
          stack.pop();
        } else {
          canceled++;
        }
      }
    }

    // return score // part 1
    return canceled; // part 2
  }

  public static void main(String[] args) {
    String[] inputs = {
        "<>", // 0 characters",
        "<random characters>", // 17 characters",
        "<<<<>", // 3 characters",
        "<{!>}>", // 2 characters",
        "<!!>", // 0 characters",
        "<!!!>>", // 0 characters",
        "<{o\"i!a,<{i<a>"}; // 10 characters

    for (String i : inputs) {
      System.out.println(part1(i));
    }

  }
}
