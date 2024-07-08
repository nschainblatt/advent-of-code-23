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
    final int number;
    final int winningNumberCount;

    Card(int number, int winningNumberCount) {
      this.number = number;
      this.winningNumberCount = winningNumberCount;
    }

    @Override
    public String toString() {
      return "\nCard: " + number + " has " + winningNumberCount + " winning card numbers\nCopies:\n";
    }
  }

  private static Card processLine(String line) {
    String[] linePart = line.split("\\|");
    String myNumbersAsString = linePart[1];
    String winningNumbersAsString = linePart[0].split("\\:")[1];
    int cardNumber = Integer.parseInt(linePart[0].split("\\:")[0].trim().chars()
        .filter(Character::isDigit)
        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString());
    List<Integer> myNumbers = parseLineNumbers(myNumbersAsString);
    List<Integer> winningNumbers = parseLineNumbers(winningNumbersAsString);
    int winningNumberCount = getWinningNumberCount(myNumbers, winningNumbers);
    return new Card(
        cardNumber,
        winningNumberCount);
  }

  public static int processInput(String[] lines) {
    int scratchCardCount = 0;
    Map<Integer, Integer> copiedCardMap = new HashMap<>();

    for (int i = 0; i < lines.length; i++) {
      final Card currentCard = processLine(lines[i]);
      int numberOfTimesToProcessCurrentCard = 1 + copiedCardMap.getOrDefault(currentCard.number, 0);
      int startingCardNumber = i + 2;
      int endingCardNumber = startingCardNumber + currentCard.winningNumberCount;

      for (int j = 0; j < numberOfTimesToProcessCurrentCard; j++) {
        scratchCardCount++;
        addCopiedCards(copiedCardMap, currentCard, startingCardNumber, endingCardNumber);
      }
    }

    return scratchCardCount;
  }

  private static void addCopiedCards(Map<Integer, Integer> copiedCardMap, Card card,
      int startingCardNumber, int endingCardNumber) {
    for (int c = startingCardNumber; c < endingCardNumber; c++) {
      copiedCardMap.merge(c, 1, Integer::sum);
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
