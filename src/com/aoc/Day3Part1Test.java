package com.aoc;

import java.util.Map;

import static com.aoc.Day3Part1.parseLine;

public class Day3Part1Test {
  public static void main(String[] args) {
    test1();
  }

  private static void test1() {
    String line = "...407...570..218....";
    ParsedLine parsedLine = parseLine(1, line);

    Map<Integer, Integer[]> correctNumberLocations = Map.of(407, new Integer[] {3, 5}, 570, new Integer[] {9, 11}, 218, new Integer[] {14, 16});
    ParsedLine correctlyParsedLine = new ParsedLine(1);
    correctlyParsedLine.numberLocations = correctNumberLocations;

    System.out.println(parsedLine.equals(correctlyParsedLine));

    for (Map.Entry<Integer, Integer[]> entry : parsedLine.numberLocations.entrySet()) {
      Integer key = entry.getKey();
      Integer[] values = entry.getValue();
      System.out.printf("Line: %d, Number: %d, Starting index: %d, Ending index: %d", parsedLine.number, key,
          values[0], values[1]);
      System.out.println();
    }

    System.out.println();
    for (Map.Entry<Integer, Integer[]> entry : correctlyParsedLine.numberLocations.entrySet()) {
      Integer key = entry.getKey();
      Integer[] values = entry.getValue();
      System.out.printf("Line: %d, Number: %d, Starting index: %d, Ending index: %d", correctlyParsedLine.number, key,
          values[0], values[1]);
      System.out.println();
    }

  }

}
