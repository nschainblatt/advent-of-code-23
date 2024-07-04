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

  // TODO:
  // Example input passes test
  // Need to cache original card result to apply for each copied version of that
  // card to optimize
  // Need to make solution pass
  public static int processInput(String[] lines) {
    int scratchCardCount = 0;
    Map<String, Integer> copiedCardMap = new HashMap<>();
    Map<String, Integer> originalCardMap = new HashMap<>(); // TODO: Cache

    for (int i = 0; i < lines.length; i++) {
      final int nextCardNumber = i + 2; // Card starts at 1, 2, 3.. vs index 0, 1, 2
      final ProcessedLine processedLine = processLine(lines[i]);
      final int numberOfCardsToCopy = getWinningNumberCount(processedLine.myNumbers, processedLine.winningNumbers);
      int numberOfTimesToProcess = 1;
      numberOfTimesToProcess += copiedCardMap.getOrDefault(processedLine.cardName, 0);

      System.out.println(processedLine.cardName);
      System.out.println(numberOfCardsToCopy);
      System.out.printf("%d -> %d\n", nextCardNumber, nextCardNumber + numberOfCardsToCopy - 1);

      for (int j = 0; j < numberOfTimesToProcess; j++) {
        scratchCardCount++;
        for (int c = nextCardNumber; c < nextCardNumber + numberOfCardsToCopy; c++) {
          String card = "Card " + c;
          copiedCardMap.merge(card, 1, Integer::sum);
        }
      }

      System.out.println(copiedCardMap.toString());
      System.out.println();
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
