package com.aoc;

import java.util.List;

import java.util.ArrayList;

// TODO: Got the correct answer. Optimize and cleanup code and review other solutions
// Implement equals method

public class Day3Part1 {
  public static void main(String[] args) {
    AdventOfCodeApi api = new AdventOfCodeApi("3", "2023");
    String input = api.getInput();
    String[] lines = input.split("\n");
    int sum = 0;
    ParsedLine previousParsedLine = null;

    for (int i = 0; i < lines.length; i++) {
      ParsedLine parsedLine = new ParsedLine(i + 1, lines[i]);
      List<Number> numberLocations = parsedLine.parseNumberLocations();
      parsedLine.setNumberLocations(numberLocations);
      List<Symbol> symbolLocations = parsedLine.parseSymbolLocations();
      parsedLine.setSymbolLocations(symbolLocations);

      // Left + Right
      int partialSum = parsedLine.setAndSumEnginePartsFromCurrentParsedLine();

      if (i != 0 && previousParsedLine != null) {
        // Above and Above Diagonal
        partialSum += parsedLine.setAndSumEnginePartsFromPreviousParsedLine(previousParsedLine);
      }

      sum += partialSum;
      previousParsedLine = parsedLine;
    }

    System.out.println("Sum of all Engine Parts: " + sum);
  }

  static <T> boolean contains(T[] array, T element) {
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

  // Check current line numbers against current line symbols (left + right)
  int setAndSumEnginePartsFromCurrentParsedLine() {
    int partialSum = 0;
    for (Number currentNumber : this.numberLocations) {
      int possibleLeftIndex = currentNumber.startingIndex - 1;
      int possibleRightIndex = currentNumber.endingIndex + 1;
      for (Symbol currentSymbol : this.symbolLocations) {
        if ((currentSymbol.index == possibleLeftIndex || currentSymbol.index == possibleRightIndex)
            && !currentNumber.isEnginePart()) {
          partialSum += currentNumber.value;
          currentNumber.setIsEnginePart(true);
        }
      }
    }
    return partialSum;
  }

  private static int checkVerticalAndDiagonal(ParsedLine parsedLineA, ParsedLine parsedLineB) {
    int partialSum = 0;
    // Check previous line numberLocations against current line symbolLocations
    for (Number previousNumber : parsedLineA.getNumberLocations()) {
      int lineLengthA = parsedLineA.line.length();
      int offsetA = 2; // For diagonals, will be decreased if the number is the first or last item in
                       // the line since the characters to the left or right won't exist
      int startingIndex = previousNumber.startingIndex - 1;
      int endingIndex = previousNumber.endingIndex + 1;
      int i = 0; // Number value index

      if (previousNumber.endingIndex == lineLengthA - 1) {
        offsetA--;
      }

      if (previousNumber.startingIndex == 0) {
        offsetA--;
        startingIndex = previousNumber.startingIndex;
      }

      // Check above, adding one to either end for the diagonal
      Integer[] possibleVerticalIndeces = new Integer[Integer.toString(previousNumber.value).length() + offsetA];
      while (startingIndex <= endingIndex && i < possibleVerticalIndeces.length) {
        possibleVerticalIndeces[i] = startingIndex;
        startingIndex++;
        i++;
      }

      for (Symbol currentSymbol : parsedLineB.getSymbolLocations()) {
        if (Day3Part1.contains(possibleVerticalIndeces, currentSymbol.index) && !previousNumber.isEnginePart()) {
          partialSum += previousNumber.value;
          previousNumber.setIsEnginePart(true);
        }
      }
    }
    return partialSum;
  }

  int setAndSumEnginePartsFromPreviousParsedLine(ParsedLine previousParsedLine) {
    int partialSum = 0;
    partialSum += ParsedLine.checkVerticalAndDiagonal(previousParsedLine, this);
    partialSum += ParsedLine.checkVerticalAndDiagonal(this, previousParsedLine);
    return partialSum;
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
