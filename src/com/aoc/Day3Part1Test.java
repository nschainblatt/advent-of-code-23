package com.aoc;

import java.util.Map;

public class Day3Part1Test {
  public static void main(String[] args) {
    test1();
  }

  private static void test1() {
    String line = "...407...570..218....";
    ParsedLine parsedLine = new ParsedLine(1, line);

    // Correct output
    Map<Integer, Integer[]> correctNumberLocations = Map.of(407, new Integer[] { 3, 5 }, 570, new Integer[] { 9, 11 },
        218, new Integer[] { 14, 16 });
    ParsedLine correctlyParsedLine = new ParsedLine(1, line);
    correctlyParsedLine.setNumberLocations(correctNumberLocations);

    // Checking equality
    System.out.println(parsedLine.equals(correctlyParsedLine));
  }
}
