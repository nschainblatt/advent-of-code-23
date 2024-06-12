package com.aoc.day3;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import com.aoc.exception.ParsedLineTestException;

public class Day3Part1Test {
  public static void main(String[] args) throws ParsedLineTestException {
    test1();
    test2();
  }

  private static void test1() throws ParsedLineTestException {
    String line = "...407...570..218....";
    ParsedLine parsedLine = new ParsedLine(1, line);

    // Correct output
    List<Number> correctNumberLocations = Arrays.asList(new Number(407, 3, 5), new Number(570, 9, 11),
        new Number(218, 14, 16));
    ParsedLine correctlyParsedLine = new ParsedLine(1, line);
    correctlyParsedLine.setNumberLocations(correctNumberLocations);

    // Checking equality
    if (!parsedLine.equals(correctlyParsedLine)) {
      throw new ParsedLineTestException("Failed equality test");
    }
  }

  private static void test2() throws ParsedLineTestException {
    String[] lines = new String[] {
        "872%..*......%.......88*484....805....178...704........282............387...........562....614..559...750..*...........@.....417......762...",
        "......745.....3...98....................*..*...............................@....329........................130.......134....$...........*...",
        ".814.............%......829.268........220...441.316.............*740......607........*831..............*......................529.......410",
        "......=...687...........*.....*................*...*..........369.....332..........798...............956.932......................=.........",
        ".....856.*............858....283.........43.594...292................*....*.604*.........217....................44*.....676*.........752.571",
        "..........489....................951...................83...........262.243........681....*.................373....493...........-...@......"
    };

    List<ParsedLine> parsedLines = new ArrayList<>();
    int sum = 0;
    ParsedLine previousParsedLine = null;
    for (int i = 0; i < lines.length; i++) {
      ParsedLine parsedLine = new ParsedLine(i + 1, lines[i]);
      List<Number> numberLocations = parsedLine.parseNumberLocations();
      parsedLine.setNumberLocations(numberLocations);
      List<Symbol> symbolLocations = parsedLine.parseSymbolLocations();
      parsedLine.setSymbolLocations(symbolLocations);

      int partialSum = parsedLine.setAndSumEnginePartsFromCurrentParsedLine();

      if (i != 0 && previousParsedLine != null) {
        partialSum += parsedLine.setAndSumEnginePartsFromPreviousParsedLine(previousParsedLine);
      }

      sum += partialSum;
      previousParsedLine = parsedLine;
      parsedLines.add(parsedLine); // TEMP
    }
    System.out.println("Engine part sum:" + sum);

    int numCount = 0;
    int engineNumCount = 0;
    int sum2 = 0;
    for (ParsedLine parsedLine : parsedLines) {
      for (Number number : parsedLine.getNumberLocations()) {
        if (number.isEnginePart()) {
          sum2 += number.value;
          engineNumCount++;
        }
        numCount++;
        System.out.println(number);
        System.out.println();
      }
    }
    System.out.println(sum);
    System.out.println(sum2);
    System.out.println("Total number count: " + numCount);
    System.out.println("Total engine part count: " + engineNumCount);
  }
}
