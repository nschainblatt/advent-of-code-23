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

  private static class Card {
    final String name;
    final List<Integer> myNumbers;
    final List<Integer> winningNumbers;

    Card(String name, List<Integer> myNumbers, List<Integer> winningNumbers) {
      this.name = name;
      this.myNumbers = myNumbers;
      this.winningNumbers = winningNumbers;
    }
  }

  private static Card processLine(String line) {
    String[] linePart = line.split("\\|");
    String myNumbersAsString = linePart[1];
    String winningNumbersAsString = linePart[0].split("\\:")[1];
    String cardName = linePart[0].split("\\:")[0].trim();
    return new Card(
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
      final Card currentCard = processLine(lines[i]);
      final int numberOfCardsToCopy = getWinningNumberCount(currentCard.myNumbers, currentCard.winningNumbers);
      int numberOfTimesToProcessCurrentCard = 1 + copiedCardMap.getOrDefault(currentCard.name, 0);

      System.out.println(currentCard.name);
      System.out.println(numberOfCardsToCopy);

      int startingCardNumber = i + 2;
      int endingCardNumber = startingCardNumber + numberOfCardsToCopy;
      for (int j = 0; j < numberOfTimesToProcessCurrentCard; j++) {
        scratchCardCount++;
        addCopiedCards(copiedCardMap, startingCardNumber, endingCardNumber);
      }

      System.out.println(copiedCardMap.toString());
      System.out.println();
    }

    return scratchCardCount;
  }

  private static void addCopiedCards(Map<String, Integer> copiedCardMap, int startingCardNumber, int endingCardNumber) {
    for (int c = startingCardNumber; c < endingCardNumber; c++) {
      String card = "Card " + c;
      copiedCardMap.merge(card, 1, Integer::sum);
    }
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
