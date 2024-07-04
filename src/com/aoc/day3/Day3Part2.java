package com.aoc.day3;

import java.util.List;
import java.util.ArrayList;

import com.aoc.Day;

public class Day3Part2 implements Day {

  public int solve(String[] lines) {
    int gearSum = 0;
    ParsedLine previousParsedLine = null;

    for (int i = 0; i < lines.length; i++) {
      ParsedLine parsedLine = new ParsedLine(i + 1, lines[i]);
      parsedLine.parseLine();
      gearSum += parsedLine.calculateGearSum(previousParsedLine);
      previousParsedLine = parsedLine;
    }

    return gearSum;
  }

  public static <T> boolean contains(T[] array, T element) {
    for (T t : array) {
      if (t.equals(element)) {
        return true;
      }
    }
    return false;
  }

  public static class ParsedLine {
    final int number;
    final String line;
    private List<Number> numberLocations;
    private List<Symbol> symbolLocations;

    public List<Number> getNumberLocations() {
      return this.numberLocations;
    }

    public List<Symbol> getSymbolLocations() {
      return this.symbolLocations;
    }

    public void setNumberLocations(List<Number> numberLocations) {
      this.numberLocations = numberLocations;
    }

    public void setSymbolLocations(List<Symbol> symbolLocations) {
      this.symbolLocations = symbolLocations;
    }

    public ParsedLine(int number, String line) {
      this.number = number;
      this.line = line;
      this.numberLocations = new ArrayList<>();
      this.symbolLocations = new ArrayList<>();
    }

    public int calculateGearSum(ParsedLine previousParsedLine) {
      int sum = 0;
      if (previousParsedLine == null) {
        return sum;
      }

      // Current line numbers against current line symbols
      sum += ParsedLine.calculateGearSum(this, this);

      // Current line numbers against previous line symbols
      sum += ParsedLine.calculateGearSum(this, previousParsedLine);

      // Previous line numbers against previous line symbols
      sum += ParsedLine.calculateGearSum(previousParsedLine, this);
      return sum;
    }

    private static int calculateGearSum(ParsedLine parsedLineA, ParsedLine parsedLineB) {
      int sum = 0;
      for (Number numberA : parsedLineA.getNumberLocations()) {
        // Horizontal
        int possibleLeftIndex = numberA.startingIndex - 1;
        int possibleRightIndex = numberA.endingIndex + 1;

        // Vertical
        int lineLengthA = parsedLineA.line.length();
        int lastIndexInLine = lineLengthA - 1;
        int startingIndex = numberA.startingIndex - 1;
        int endingIndex = numberA.endingIndex + 1;
        int diagonalOffset = 2;

        if (numberA.endingIndex == lastIndexInLine) {
          diagonalOffset--;
        }

        if (numberA.startingIndex == 0) {
          diagonalOffset--;
          startingIndex = numberA.startingIndex;
        }

        Integer[] possibleVerticalIndeces = new Integer[Integer.toString(numberA.value).length()
            + diagonalOffset];

        int i = 0;
        while (startingIndex <= endingIndex && i < possibleVerticalIndeces.length) {
          possibleVerticalIndeces[i] = startingIndex;
          startingIndex++;
          i++;
        }

        for (Symbol symbolB : parsedLineB.getSymbolLocations()) {
          if (symbolB.index > endingIndex) {
            break;
          }

          if (possibleLeftIndex == symbolB.index || possibleRightIndex == symbolB.index
              || (Day3Part1.contains(possibleVerticalIndeces, symbolB.index) && !numberA.isEnginePart())) {
            if (symbolB.value == '*') {
              symbolB.enginePartNumbers.add(numberA.value);
              if (symbolB.enginePartNumbers.size() == 2) {
                sum += symbolB.enginePartNumbers.stream().reduce((acc, next) -> acc * next).orElse(0);
              }
            }
            numberA.setIsEnginePart(true);
          }
        }
      }

      return sum;
    }

    /**
     * Parsed line instance field and set NumberLocations and SymbolLocations
     */
    public void parseLine() {
      boolean inDigit = false;
      int digitStartingIndex = -1;
      Character previousCharacter = null;
      char[] characters = this.line.toCharArray();
      StringBuilder digitBuilder = new StringBuilder("");
      List<Number> numberLocations = new ArrayList<>();
      List<Symbol> symbolLocations = new ArrayList<>();

      for (int i = 0; i < characters.length; i++) {
        if (this.isSymbol(characters[i])) {
          symbolLocations.add(new Symbol(characters[i], i));
        }

        if (Character.isDigit(characters[i])) {
          if (previousCharacter == null || !Character.isDigit(previousCharacter)) {
            digitStartingIndex = i;
          }
          inDigit = true;
          digitBuilder.append(characters[i]);
        }

        if ((!Character.isDigit(characters[i]) || i == characters.length - 1) && inDigit) {
          int endingIndex = i - 1;
          if (i == characters.length - 1) {
            endingIndex = i;
          }
          Number numberLocation = new Number(Integer.parseInt(digitBuilder.toString()), digitStartingIndex,
              endingIndex);
          numberLocations.add(numberLocation);
          inDigit = false;
          digitStartingIndex = -1;
          digitBuilder.delete(0, digitBuilder.length());
        }

        previousCharacter = characters[i];
      }

      this.numberLocations = numberLocations;
      this.symbolLocations = symbolLocations;
    }

    private boolean isSymbol(char c) {
      return !(Character.isDigit(c) || c == '.');
    }
  }

  public static class Number {
    public final int value;
    public final int startingIndex;
    public final int endingIndex;
    private boolean enginePart = false;

    public Number(int value, int startingIndex, int endingIndex) {
      this.value = value;
      this.startingIndex = startingIndex;
      this.endingIndex = endingIndex;
    }

    public boolean isEnginePart() {
      return this.enginePart;
    }

    private void setIsEnginePart(boolean isEnginePart) {
      this.enginePart = isEnginePart;
    }
  }

  public static class Symbol {
    public final char value;
    public final int index;
    private final List<Integer> enginePartNumbers = new ArrayList<>();

    public Symbol(char value, int index) {
      this.value = value;
      this.index = index;
    }
  }
}
