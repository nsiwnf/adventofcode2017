package com.adventofcode.advent2017;

public class Day10 {
  static int part1(int[] input, int[] string) {
    int index = 0;
    int skipSize = 0;
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

    return string[0] * string[1];
  }
  
  private static char[] HEX_MAP = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

  static String part2(byte[] input1, int[] string) {
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

    StringBuilder b = new StringBuilder(32);
    byte[] hashes = new byte[16];
    for (int hashidx = 0; hashidx < 16; hashidx++) {
      for (int i = 0; i < 16; i++) {
        int a = string[16 * hashidx + i];
        hashes[hashidx] ^= a;
      }

      b.append(HEX_MAP[hashes[hashidx] >> 4 & 0x0F])
          .append(HEX_MAP[hashes[hashidx] & 0x0F]);
    }

    return b.toString();
  }

  public static void main(String[] args) {
    int[] input = {212, 254, 178, 237, 2, 0, 1, 54, 167, 92, 117, 125, 255, 61, 159, 164};
    String inputAsString = "212,254,178,237,2,0,1,54,167,92,117,125,255,61,159,164";
    
    int[] string = new int[256];
    for (int i = 0; i < 256; i++) {
      string[i] = i;
    }

    int[] testString = {0, 1, 2, 3, 4};
    int[] testInput = {3, 4, 1, 5};

//    System.out.println(part1(testInput, testString)); // 12
//    System.out.println(part1(input, string)); // 212
    System.out.println(part2(inputAsString.getBytes(), string)); //96DE9657665675B51CD03F0B3528BA26
  }
}
