package com.aoc.day4;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.aoc.Day;

public class Day4Part1 implements Day {

  public int solve(String[] input) {
    // The card will be index + 1
    Map<String, List<List<Integer>>> processedInput = processInput(input);
    int sum = getWinningPoints(processedInput.get("myNumbers"), processedInput.get("winningNumbers"));

    return sum;
  }

  public static Map<String, List<List<Integer>>> processInput(String[] input) {
    List<List<Integer>> myNumbers = new ArrayList<>();
    List<List<Integer>> winningNumbers = new ArrayList<>();

    for (String line : input) {
      String[] linePart = line.split("\\|");
      String myNumbersAsString = linePart[1];
      String winningNumbersAsString = linePart[0].split("\\:")[1];
      myNumbers.add(parseLineNumbers(myNumbersAsString));
      winningNumbers.add(parseLineNumbers(winningNumbersAsString));
    }

    return Map.of("myNumbers", myNumbers, "winningNumbers", winningNumbers);
  }

  private static List<Integer> parseLineNumbers(String linePart) {
    return Arrays.stream(linePart.trim().split(" "))
        .filter(Predicate.not(String::isEmpty))
        .mapToInt(s -> Integer.parseInt(s.trim())).boxed().collect(Collectors.toList());
  }

  private static int getWinningPoints(List<List<Integer>> myNumbers, List<List<Integer>> winningNumbers) {
    int totalPointSum = 0;
    int currentCardPoints = 0;

    // NOTE: Both outer lists are the same size, their elements belong to the same
    // card/row
    for (int i = 0; i < myNumbers.size(); i++) {
      for (Integer cardWinningNumber : winningNumbers.get(i)) {
        if (myNumbers.get(i).contains(cardWinningNumber)) {
          currentCardPoints = currentCardPoints == 0 ? 1 : currentCardPoints * 2;
        }
      }
      totalPointSum += currentCardPoints;
      currentCardPoints = 0;
    }

    return totalPointSum;
  }
}
