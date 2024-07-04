package com.aoc.day4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.aoc.Day;

public class Day4Part2 implements Day {

  public int solve(String[] input) {
    int sum = processInput(input);
    return sum;
  }

  private static class ProcessedLine {
    final String cardName;
    final List<Integer> myNumbers;
    final List<Integer> winningNumbers;

    ProcessedLine(String cardName, List<Integer> myNumbers, List<Integer> winningNumbers) {
      this.cardName = cardName;
      this.myNumbers = myNumbers;
      this.winningNumbers = winningNumbers;
    }
  }

  private static ProcessedLine processLine(String line) {
    String[] linePart = line.split("\\|");
    String myNumbersAsString = linePart[1];
    String winningNumbersAsString = linePart[0].split("\\:")[1];
    String cardName = linePart[0].split("\\:")[0].trim();
    return new ProcessedLine(
        cardName,
        parseLineNumbers(myNumbersAsString),
        parseLineNumbers(winningNumbersAsString));
  }

  public static int processInput(String[] input) {
    int scratchCardCount = 0;

    for (String line : input) {
      scratchCardCount++; // Original card
      ProcessedLine processedLine = processLine(line);
      int numberOfCardsToCopy = getWinningNumberCount(processedLine.myNumbers, processedLine.winningNumbers);
      // TODO: copy cards and store in hashmap
    }

    return scratchCardCount;
  }

  public static List<Integer> parseLineNumbers(String linePart) {
    return Arrays.stream(linePart.trim().split(" "))
        .filter(Predicate.not(String::isEmpty))
        .mapToInt(s -> Integer.parseInt(s.trim())).boxed().collect(Collectors.toList());
  }

  private static int getWinningNumberCount(List<Integer> myNumbers, List<Integer> winningNumbers) {
    int winningNumberCount = 0;

    for (Integer winningNumber : winningNumbers) {
      if (myNumbers.contains(winningNumber)) {
        winningNumberCount++;
      }
    }

    return winningNumberCount;
  }
}
