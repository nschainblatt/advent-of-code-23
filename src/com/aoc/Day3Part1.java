package com.aoc;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.aoc.AdventOfCodeApi;

public class Day3Part1 {
  public static void main(String[] args) {
    AdventOfCodeApi api = new AdventOfCodeApi("3", "2023");
    String input = api.getInput();

    // If a number is adjacent to a non . symbol, then that number is an engine
    // part.
    // Goal: Get the sum of all engine part numbers.

    // Plan
    // 1. Compare the current lines symbol locations to previous lines number locations
    // locations
    // (except first line obviously).
    // 2. Compare the current lines number locations to previous lines symbol
    // locations
    // 3. Compare the current lines number locations to current lines symbol
    // locations
    // 4. Ensure your not creating duplicate engine parts, you need to mark the
    // already known engine parts to get an accurate sum
    // 5. Repeat

    String[] lines = input.split("\n");
    List<ParsedLine> parsedLines = new ArrayList<>();

    for (int i = 0; i < lines.length; i++) {
      ParsedLine parsedLine = parseLine(i + 1, lines[i]);
      parsedLines.add(parsedLine);
    }

    // Checking the first line
    for (Map.Entry<Integer, Integer[]> entry : parsedLines.get(0).numberLocations.entrySet()) {
      Integer key = entry.getKey();
      Integer[] values = entry.getValue();
      System.out.printf("Line: %d, Number: %d, Starting index: %d, Ending index: %d", parsedLines.get(0).number, key,
          values[0], values[1]);
      System.out.println();
    }
  }

  // Note: may need to change to a long depending on the size of the sum of engine
  // part numbers
  public static ParsedLine parseLine(int lineNumber, String line) {
    ParsedLine parsedLine = new ParsedLine(lineNumber);
    char[] characters = line.toCharArray();

    boolean inDigit = false;
    StringBuilder digitBuilder = new StringBuilder("");

    // Setting to negative one so it will fail when used as index so I can find out
    // if it's failing
    int digitStartingIndex = -1;
    Character previousCharacter = null;

    // Loop for getting the numbers from the line and their indeces
    for (int i = 0; i < characters.length; i++) {
      if (Character.isDigit(characters[i])) {
        if (previousCharacter == null || !Character.isDigit(previousCharacter)) {
          digitStartingIndex = i;
        }
        inDigit = true;
        digitBuilder.append(characters[i]);
      }

      if (characters[i] == '.' && inDigit) {
        parsedLine.numberLocations.put(Integer.parseInt(digitBuilder.toString()),
            new Integer[] { digitStartingIndex, i - 1 });
        inDigit = false;
        digitStartingIndex = -1;
        digitBuilder.delete(0, digitBuilder.length());
      }

      previousCharacter = characters[i];
    }

    return parsedLine;
  }

  static class ParsedLine {
    int number;
    Map<Integer, Integer[]> numberLocations;
    Map<Character, Integer[]> symbolLocations;

    ParsedLine(int number) {
      this.number = number;
      this.numberLocations = new HashMap<Integer, Integer[]>();
      this.symbolLocations = new HashMap<Character, Integer[]>();
    }
  }
}
