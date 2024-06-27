package com.aoc.day3;

import java.util.List;
import java.util.ArrayList;

import com.aoc.api.AdventOfCodeApi;
import com.aoc.Day;

// TODO:
// 1. Cleanup DONE
// 2. Optimize my way TODO
// 3. Implement equality method DONE
// 4. Use equality method in tests DONE
// 5. Review other solutions TODO
// 6. Implement API and remove sessionCookie from current code DONE
// 7. Edit git history to remove cookie DONE
// 8. Refactor tests DONE 

public class Day3Part1 implements Day {
  private AdventOfCodeApi api;

  public Day3Part1(AdventOfCodeApi api) {
    this.api = api;
  }

  public int solve() {
    String[] lines = api.getInput(2023, 3);
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

    return sum;
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

    public int getEnginePartSumFromCurrentLine() {
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

    public int getEnginePartSumFromPreviousLine(ParsedLine previousParsedLine) {
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

        Integer[] possibleVerticalIndeces = new Integer[Integer.toString(previousNumber.value).length()
            + diagonalOffset];

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

    public List<Symbol> parseSymbolLocations() {
      char[] characters = this.line.toCharArray();
      List<Symbol> symbolLocations = new ArrayList<>();

      for (int i = 0; i < characters.length; i++) {
        if (ParsedLine.isSymbol(characters[i])) {
          symbolLocations.add(new Symbol(characters[i], i));
        }
      }

      return symbolLocations;
    }

    public List<Number> parseNumberLocations() {
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
          Number numberLocation = new Number(Integer.parseInt(digitBuilder.toString()), digitStartingIndex,
              endingIndex);
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
      if (other == null) {
        return false;
      }
      if (ParsedLine.class.isInstance(other)) {
        ParsedLine otherParsedLine = (ParsedLine) other;
        if (this.number == otherParsedLine.number && this.line.equals(otherParsedLine.line)) {
          if (this.numberLocations.size() == otherParsedLine.numberLocations.size()) {
            if (this.numberLocations.equals(otherParsedLine.numberLocations)) {
              return true;
            }
          }
        }
      }
      return false;
    }

    static boolean isSymbol(char c) {
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

    @Override
    public String toString() {
      return String.format("Value: %d, Starting Index: %d, Ending Index: %d, Engine Part: %b", this.value,
          this.startingIndex, this.endingIndex, this.enginePart);
    }

    @Override
    public boolean equals(Object other) {
      if (other == null) {
        return false;
      }
      if (Number.class.isInstance(other)) {
        Number otherSymbol = (Number) other;
        if (this.value == otherSymbol.value && this.startingIndex == otherSymbol.startingIndex
            && this.endingIndex == otherSymbol.endingIndex) {
          return true;
        }
      }
      return false;
    }
  }

  public static class Symbol {
    public final char value;
    public final int index;

    public Symbol(char value, int index) {
      this.value = value;
      this.index = index;
    }

    @Override
    public String toString() {
      return String.format("Value: %c, Index: %d", this.value, this.index);
    }

    @Override
    public boolean equals(Object other) {
      if (other == null) {
        return false;
      }
      if (Symbol.class.isInstance(other)) {
        Symbol otherSymbol = (Symbol) other;
        if (this.value == otherSymbol.value && this.index == otherSymbol.index) {
          return true;
        }
      }
      return false;
    }
  }
}
