package com.aoc.day4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.Comparator;

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
    final int winningNumberCount;
    List<String> namesOfCardCopies = new ArrayList<>();

    Card(String name, List<Integer> myNumbers, List<Integer> winningNumbers, int winningNumberCount) {
      this.name = name;
      this.myNumbers = myNumbers;
      this.winningNumbers = winningNumbers;
      this.winningNumberCount = winningNumberCount;
    }

    @Override
    public String toString() {
      return "\n" + name + " has " + winningNumberCount + " winning card numbers\nCopies:\n"
          + namesOfCardCopies.toString()
          + "\n";
    }
  }

  private static Card processLine(String line) {
    String[] linePart = line.split("\\|");
    String myNumbersAsString = linePart[1];
    String winningNumbersAsString = linePart[0].split("\\:")[1];
    String cardName = linePart[0].split("\\:")[0].trim();
    List<Integer> myNumbers = parseLineNumbers(myNumbersAsString);
    List<Integer> winningNumbers = parseLineNumbers(winningNumbersAsString);
    int winningNumberCount = getWinningNumberCount(myNumbers, winningNumbers);
    return new Card(
        cardName,
        myNumbers,
        winningNumbers,
        winningNumberCount);
  }

  public static int processInput(String[] lines) {
    int scratchCardCount = 0;
    Map<String, Integer> copiedCardMap = new HashMap<>();

    List<Card> listOfCards = new ArrayList<>();

    for (int i = 0; i < lines.length; i++) {
      final Card currentCard = processLine(lines[i]);
      int numberOfTimesToProcessCurrentCard = 1 + copiedCardMap.getOrDefault(currentCard.name, 0);
      int startingCardNumber = i + 2;
      int endingCardNumber = startingCardNumber + currentCard.winningNumberCount;

      for (int j = 0; j < numberOfTimesToProcessCurrentCard; j++) {
        scratchCardCount++;
        addCopiedCards(copiedCardMap, currentCard, startingCardNumber, endingCardNumber);
      }
      listOfCards.add(currentCard);
    }

    for (Card card : listOfCards) {
      // card.namesOfCardCopies.sort(Comparator.naturalOrder());
      System.out.println(card);
    }
    return scratchCardCount;
  }

  private static void addCopiedCards(Map<String, Integer> copiedCardMap, Card card,
      int startingCardNumber, int endingCardNumber) {
    for (int c = startingCardNumber; c < endingCardNumber; c++) {
      String name = "Card " + c;
      card.namesOfCardCopies.add(name);
      copiedCardMap.merge("Card " + c, 1, Integer::sum);
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
        // if (countOccurenceOfNumber(myNumbers, winningNumber) > 1) {
        //   System.out.println("DEBUG");
        // }
        winningNumberCount++;
      }
    }

    return winningNumberCount;
  }

  private static int countOccurenceOfNumber(List<Integer> numbers, Integer number) {
    int count = 0;
    for (Integer n : numbers) {
      if (n.equals(number)) {
        count++;
      }
    }
    return count;
  }
}
