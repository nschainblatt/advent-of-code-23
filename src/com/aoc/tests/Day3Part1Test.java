package com.aoc.tests;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.aoc.day3.Day3Part1;
import com.aoc.exception.ParsedLineTestException;

public class Day3Part1Test {
  public static void run() throws ParsedLineTestException {
    System.out.printf("Test 1: %s\n", test1() ? "PASSED" : "FAILED");
    System.out.printf("Test 2: %s\n", test2() ? "PASSED" : "FAILED");
  }

  private static boolean test1() throws ParsedLineTestException {
    String line = "...407...570..218....";
    Day3Part1.ParsedLine parsedLine = new Day3Part1.ParsedLine(1, line);
    List<Day3Part1.Number> numberLocations = parsedLine.parseNumberLocations();
    List<Day3Part1.Symbol> symbolLocations = parsedLine.parseSymbolLocations();
    parsedLine.setNumberLocations(numberLocations);
    parsedLine.setSymbolLocations(symbolLocations);

    List<Day3Part1.Number> correctNumberLocations = Arrays.asList(new Day3Part1.Number(407, 3, 5),
        new Day3Part1.Number(570, 9, 11),
        new Day3Part1.Number(218, 14, 16));
    Day3Part1.ParsedLine correctlyParsedLine = new Day3Part1.ParsedLine(1, line);
    correctlyParsedLine.setNumberLocations(correctNumberLocations);

    if (!parsedLine.equals(correctlyParsedLine)) {
      return false;
    }
    return true;
  }

  private static boolean test2() {
    String[] lines = new String[] {
        "872%..*......%.......88*484....805....178...704........282............387...........562....614..559...750..*...........@.....417......762...",
        "......745.....3...98....................*..*...............................@....329........................130.......134....$...........*...",
        ".814.............%......829.268........220...441.316.............*740......607........*831..............*......................529.......410",
        "......=...687...........*.....*................*...*..........369.....332..........798...............956.932......................=.........",
        ".....856.*............858....283.........43.594...292................*....*.604*.........217....................44*.....676*.........752.571",
        "..........489....................951...................83...........262.243........681....*.................373....493...........-...@......"
    };
    List<Day3Part1.ParsedLine> parsedLines = new ArrayList<>();
    int sumA = 0;
    Day3Part1.ParsedLine previousParsedLine = null;

    for (int i = 0; i < lines.length; i++) {
      Day3Part1.ParsedLine parsedLine = new Day3Part1.ParsedLine(i + 1, lines[i]);
      List<Day3Part1.Number> numberLocations = parsedLine.parseNumberLocations();
      parsedLine.setNumberLocations(numberLocations);
      List<Day3Part1.Symbol> symbolLocations = parsedLine.parseSymbolLocations();
      parsedLine.setSymbolLocations(symbolLocations);
      int partialSum = parsedLine.getEnginePartSumFromCurrentLine();

      if (i != 0 && previousParsedLine != null) {
        partialSum += parsedLine.getEnginePartSumFromPreviousLine(previousParsedLine);
      }

      sumA += partialSum;
      previousParsedLine = parsedLine;
      parsedLines.add(parsedLine);
    }

    int sumB = 0;
    for (Day3Part1.ParsedLine parsedLine : parsedLines) {
      for (Day3Part1.Number number : parsedLine.getNumberLocations()) {
        if (number.isEnginePart()) {
          sumB += number.value;
        }
      }
    }
    if (sumA != sumB) {
      return false;
    }
    return true;
  }
}
