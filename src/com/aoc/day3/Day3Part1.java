package com.aoc.day3;

import java.util.List;
import java.util.ArrayList;

import com.aoc.api.AdventOfCodeApi;

// TODO:
// 1. Cleanup DONE
// 2. Optimize my way
// 3. Implement equality method
// 4. Use equality method in tests
// 5. Review other solutions
// 6. Fix sessionCookie problem look at notes

public class Day3Part1 {
  public static void main(String[] args) {
    AdventOfCodeApi api = new AdventOfCodeApi("3", "2023");
    String[] lines = api.getInput();
    int sum = 0;
    ParsedLine previousParsedLine = null;

    for (int i = 0; i < lines.length; i++) {
      ParsedLine parsedLine = new ParsedLine(i + 1, lines[i]);
      List<Number> numberLocations = parsedLine.parseNumberLocations();
      List<Symbol> symbolLocations = parsedLine.parseSymbolLocations();
      parsedLine.setNumberLocations(numberLocations);
      parsedLine.setSymbolLocations(symbolLocations);
      int partialSum = parsedLine.getEnginePartSumFromCurrentLine();

      if (i != 0 && previousParsedLine != null) {
        partialSum += parsedLine.getEnginePartSumFromPreviousLine(previousParsedLine);
      }

      sum += partialSum;
      previousParsedLine = parsedLine;
    }

    System.out.println("Sum of all Engine Parts: " + sum);
  }

  public static <T> boolean contains(T[] array, T element) {
    for (T t : array) {
      if (t.equals(element)) {
        return true;
      }
    }
    return false;
  }
}

class Number {
  final int value;
  final Integer startingIndex;
  final Integer endingIndex;
  private boolean enginePart = false;

  Number(int value, Integer startingIndex, Integer endingIndex) {
    this.value = value;
    this.startingIndex = startingIndex;
    this.endingIndex = endingIndex;
  }

  boolean isEnginePart() {
    return this.enginePart;
  }

  void setIsEnginePart(boolean isEnginePart) {
    this.enginePart = isEnginePart;
  }

  @Override
  public String toString() {
    return String.format("Value: %d, Starting Index: %d, Ending Index: %d, Engine Part: %b", this.value,
        this.startingIndex, this.endingIndex, this.enginePart);
  }
}

class Symbol {
  final char value;
  final Integer index;

  Symbol(char value, Integer index) {
    this.value = value;
    this.index = index;
  }

  @Override
  public String toString() {
    return String.format("Value: %c, Index: %d", this.value, this.index);
  }
}

class ParsedLine {
  final int number;
  final String line;
  private List<Number> numberLocations;
  private List<Symbol> symbolLocations;

  List<Number> getNumberLocations() {
    return this.numberLocations;
  }

  List<Symbol> getSymbolLocations() {
    return this.symbolLocations;
  }

  void setNumberLocations(List<Number> numberLocations) {
    this.numberLocations = numberLocations;
  }

  void setSymbolLocations(List<Symbol> symbolLocations) {
    this.symbolLocations = symbolLocations;
  }

  ParsedLine(int number, String line) {
    this.number = number;
    this.line = line;
    this.numberLocations = new ArrayList<>();
    this.symbolLocations = new ArrayList<>();
  }

  int getEnginePartSumFromCurrentLine() {
    int sum = 0;
    for (Number number : this.numberLocations) {
      int possibleLeftIndex = number.startingIndex - 1;
      int possibleRightIndex = number.endingIndex + 1;
      for (Symbol symbol : this.symbolLocations) {
        if ((symbol.index == possibleLeftIndex || symbol.index == possibleRightIndex)
            && !number.isEnginePart()) {
          sum += number.value;
          number.setIsEnginePart(true);
        }
      }
    }
    return sum;
  }

  int getEnginePartSumFromPreviousLine(ParsedLine previousParsedLine) {
    int sum = 0;
    sum += ParsedLine.checkVerticalAndDiagonal(previousParsedLine, this);
    sum += ParsedLine.checkVerticalAndDiagonal(this, previousParsedLine);
    return sum;
  }

  private static int checkVerticalAndDiagonal(ParsedLine parsedLineA, ParsedLine parsedLineB) {
    int sum = 0;
    for (Number previousNumber : parsedLineA.getNumberLocations()) {
      int lineLengthA = parsedLineA.line.length();
      int lastIndexInLine = lineLengthA - 1;
      int startingIndex = previousNumber.startingIndex - 1;
      int endingIndex = previousNumber.endingIndex + 1;
      int diagonalOffset = 2;
      int i = 0;

      if (previousNumber.endingIndex == lastIndexInLine) {
        diagonalOffset--;
      }

      if (previousNumber.startingIndex == 0) {
        diagonalOffset--;
        startingIndex = previousNumber.startingIndex;
      }

      Integer[] possibleVerticalIndeces = new Integer[Integer.toString(previousNumber.value).length() + diagonalOffset];

      while (startingIndex <= endingIndex && i < possibleVerticalIndeces.length) {
        possibleVerticalIndeces[i] = startingIndex;
        startingIndex++;
        i++;
      }

      for (Symbol currentSymbol : parsedLineB.getSymbolLocations()) {
        if (Day3Part1.contains(possibleVerticalIndeces, currentSymbol.index) && !previousNumber.isEnginePart()) {
          sum += previousNumber.value;
          previousNumber.setIsEnginePart(true);
        }
      }
    }

    return sum;
  }

  List<Symbol> parseSymbolLocations() {
    char[] characters = this.line.toCharArray();
    List<Symbol> symbolLocations = new ArrayList<>();

    for (int i = 0; i < characters.length; i++) {
      if (ParsedLine.isSymbol(characters[i])) {
        symbolLocations.add(new Symbol(characters[i], i));
      }
    }

    return symbolLocations;
  }

  List<Number> parseNumberLocations() {
    boolean inDigit = false;
    int digitStartingIndex = -1;
    Character previousCharacter = null;
    char[] characters = this.line.toCharArray();
    StringBuilder digitBuilder = new StringBuilder("");
    List<Number> numberLocations = new ArrayList<>();

    for (int i = 0; i < characters.length; i++) {
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
        Number numberLocation = new Number(Integer.parseInt(digitBuilder.toString()), digitStartingIndex, endingIndex);
        numberLocations.add(numberLocation);
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
