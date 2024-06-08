package com.aoc;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.aoc.AdventOfCodeApi;

// Plan:
// Parse current line and gather symbol and number locations.
// Check current line against previous line symbol locations. (except first line obviously)
// Check current line against current line symbol locations.
// Store the current parsed line for the next line to have access too
// Go to next line and repeat...
//
// Remember:
// Any number that is next to or diagnol to a symbol is counted as an engine part (. is not a symbol)
// Do not count a number twice if next to two symbols
// Gather the sum of all engine part numbers

public class Day3Part1 {
  public static void main(String[] args) {
    AdventOfCodeApi api = new AdventOfCodeApi("3", "2023");
    String input = api.getInput();
    String[] lines = input.split("\n");
    List<ParsedLine> parsedLines = new ArrayList<>();

    for (int i = 0; i < lines.length; i++) {
      ParsedLine parsedLine = parseLine(i + 1, lines[i]);
      parsedLines.add(parsedLine);
    }

  }

  static boolean isSymbol(char c) {
    return !(Character.isDigit(c) || c == '.');
  }

  // TODO: may need to change to a long
  static ParsedLine parseLine(int lineNumber, String line) {
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

}

class ParsedLine {
  int number;
  Map<Integer, Integer[]> numberLocations;
  Map<Character, Integer[]> symbolLocations;

  ParsedLine(int number) {
    this.number = number;
    this.numberLocations = new HashMap<Integer, Integer[]>();
    this.symbolLocations = new HashMap<Character, Integer[]>();
  }

  @Override
  public boolean equals(Object other) {
    System.out.println("Testing equality");
    if (other == null) {
      return false;
    }
    if (other instanceof ParsedLine) {
      return true;
    }
    return false;
  }
}
