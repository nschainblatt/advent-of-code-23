package com.aoc.day3;

import com.aoc.Day;

public class Day3Part1Optimized implements Day {
  public int solve(String[] lines) {
    int sum = 0;

    for (int i = 0; i < lines.length; i++) {
      sum += getEnginePartSumFromLine(i, lines[i], lines);
    }

    return sum;
  }

  private boolean isEnginePartNumber(String line, String[] lines, int row, int startingIndex, int endingIndex, int maxXIndex,
      int maxYIndex) {
    // Check horizontal (left + right)
    if (startingIndex > 0) { // Can check left
      if (isSymbol(line.charAt(startingIndex - 1))) {
        return true;
      }
    }
    if (endingIndex < maxXIndex) { // Can check right
      if (isSymbol(line.charAt(endingIndex + 1))) {
        return true;
      }
    }

    // Check vertical (above + below + diagonal)
    if (row > 0) { // Can check above
      int startingAboveIndex = startingIndex;
      int endingAboveIndex = endingIndex;
      String previousLine = lines[row - 1];
      if (startingAboveIndex > 0) { // Can check left above diagonal
        startingAboveIndex -= 1;
      }
      if (endingAboveIndex < maxXIndex) { // Can check right above diagonal
        endingAboveIndex += 1;
      }
      // Loop over valid above indeces
      for (int i = startingAboveIndex; i <= endingAboveIndex; i++) {
        if (isSymbol(previousLine.charAt(i))) {
          return true;
        }
      }
    }
    if (row < maxYIndex) { // Can check below
      int startingBelowIndex = startingIndex;
      int endingBelowIndex = endingIndex;
      String nextLine = lines[row + 1];
      if (startingBelowIndex > 0) { // Can check left below diagonal
        startingBelowIndex -= 1;
      }
      if (endingBelowIndex < maxXIndex) { // Can check right below diagonal
        endingBelowIndex += 1;
      }
      // Loop over valid below indeces
      for (int i = startingBelowIndex; i <= endingBelowIndex; i++) {
        if (isSymbol(nextLine.charAt(i))) {
          return true;
        }
      }
    }
    return false;
  }

  public int getEnginePartSumFromLine(int row, String line, String[] lines) {
    int partialSum = 0;
    boolean inDigit = false;
    int digitStartingIndex = -1;
    Character previousCharacter = null;
    char[] characters = line.toCharArray();
    StringBuilder digitBuilder = new StringBuilder("");

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

        if (isEnginePartNumber(line, lines, row, digitStartingIndex, endingIndex, line.length() - 1,
            lines.length - 1)) {
          partialSum += Integer.parseInt(digitBuilder.toString());
        }

        inDigit = false;
        digitStartingIndex = -1;
        digitBuilder.delete(0, digitBuilder.length());
      }

      previousCharacter = characters[i];
    }

    return partialSum;
  }

  static boolean isSymbol(char c) {
    return !(Character.isDigit(c) || c == '.');
  }
}
