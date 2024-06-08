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
    List<ParsedLine> parsedLines = new ArrayList<>(); // Just for testing output, will just use a previousParsedLine
                                                      // variable to store the previous line for the algo
    int sum = 0;
    ParsedLine previousParsedLine = null;

    for (int i = 0; i < lines.length; i++) {
      ParsedLine parsedLine = new ParsedLine(i + 1, lines[i]);
      Map<Integer, Integer[]> numberLocations = parsedLine.parseNumberLocations();
      parsedLine.setNumberLocations(numberLocations);
      Map<Character, Integer> symbolLocations = parsedLine.parseSymbolLocations();
      parsedLine.setSymbolLocations(symbolLocations);

      parsedLines.add(parsedLine); // TEMP

      if (i != 0 && previousParsedLine != null) {
        // TODO: compare against current line and previous line
      }

      previousParsedLine = parsedLine;
    }

    for (Map.Entry<Integer, Integer[]> entry : parsedLines.get(1).getNumberLocations().entrySet()) {
      Integer key = entry.getKey();
      Integer[] values = entry.getValue();
      System.out.printf("Line: %d, Number: %d, Starting index: %d, Ending index: %d",
          parsedLines.get(1).getLineNumber(), key,
          values[0], values[1]);
      System.out.println();
    }

    System.out.println();
    for (Map.Entry<Character, Integer> entry : parsedLines.get(1).getSymbolLocations().entrySet()) {
      Character key = entry.getKey();
      Integer value = entry.getValue();
      System.out.printf("Line: %d, Symbol: %c, Index: %d",
          parsedLines.get(1).getLineNumber(), key,
          value);
      System.out.println();
    }
  }
}

class ParsedLine {
  private int number;
  private String line;
  private Map<Integer, Integer[]> numberLocations;
  private Map<Character, Integer> symbolLocations;

  int getLineNumber() {
    return this.number;
  }

  String getLine() {
    return this.line;
  }

  Map<Integer, Integer[]> getNumberLocations() {
    return this.numberLocations;
  }

  Map<Character, Integer> getSymbolLocations() {
    return this.symbolLocations;
  }

  void setNumberLocations(Map<Integer, Integer[]> numberLocations) {
    this.numberLocations = numberLocations;
  }

  void setSymbolLocations(Map<Character, Integer> symbolLocations) {
    this.symbolLocations = symbolLocations;
  }

  ParsedLine(int number, String line) {
    this.number = number;
    this.line = line;
    this.numberLocations = new HashMap<>();
    this.symbolLocations = new HashMap<>();
  }

  Map<Character, Integer> parseSymbolLocations() {
    char[] characters = this.line.toCharArray();
    Map<Character, Integer> symbolLocations = new HashMap<>();

    for (int i = 0; i < characters.length; i++) {
      if (ParsedLine.isSymbol(characters[i])) {
        symbolLocations.put(characters[i], i);
      }
    }

    return symbolLocations;
  }

  Map<Integer, Integer[]> parseNumberLocations() {
    boolean inDigit = false;
    int digitStartingIndex = -1;
    Character previousCharacter = null;
    char[] characters = this.line.toCharArray();
    StringBuilder digitBuilder = new StringBuilder("");
    Map<Integer, Integer[]> numberLocations = new HashMap<>();

    for (int i = 0; i < characters.length; i++) {
      if (Character.isDigit(characters[i])) {
        if (previousCharacter == null || !Character.isDigit(previousCharacter)) {
          digitStartingIndex = i;
        }
        inDigit = true;
        digitBuilder.append(characters[i]);
      }

      if (characters[i] == '.' && inDigit) {
        numberLocations.put(Integer.parseInt(digitBuilder.toString()),
            new Integer[] { digitStartingIndex, i - 1 });
        inDigit = false;
        digitStartingIndex = -1;
        digitBuilder.delete(0, digitBuilder.length());
      }

      previousCharacter = characters[i];
    }

    return numberLocations;
  }

  @Override
  public boolean equals(Object other) {
    System.out.println("Testing equality");
    if (other == null) {
      return false;
    }
    if (other instanceof ParsedLine) {
      System.out.println("Instance of ParsedLine");
      // TODO: finish equality test
      // return this.getNumberLocations().equals(other.getNumberLocations());
      return true;
    }
    return false;
  }

  static boolean isSymbol(char c) {
    return !(Character.isDigit(c) || c == '.');
  }
}
