package com.aoc;

import java.util.Arrays;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class Day3Part1Test {
  public static void main(String[] args) throws ParsedLineTestException {
    test1();
  }

  private static void test1() throws ParsedLineTestException {
    String line = "...407...570..218....";
    ParsedLine parsedLine = new ParsedLine(1, line);

    // Correct output
    List<Number> correctNumberLocations = Arrays.asList(new Number(407, 3, 5), new Number(570, 9, 11), new Number(218, 14, 16));
    ParsedLine correctlyParsedLine = new ParsedLine(1, line);
    correctlyParsedLine.setNumberLocations(correctNumberLocations);

    // Checking equality
    // System.out.println(parsedLine.equals(correctlyParsedLine));
    if (!parsedLine.equals(correctlyParsedLine)) {
      throw new ParsedLineTestException("Failed equality test");
    }
  }
}
