package com.aoc.day4;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.aoc.Day;

public class Day4Part1 implements Day {

  public int solve(String[] input) {
    int sum = processInput(input);
    return sum;
  }

  public static int processInput(String[] input) {
    int points = 0;

    for (String line : input) {
      String[] linePart = line.split("\\|");
      String myNumbersAsString = linePart[1];
      String winningNumbersAsString = linePart[0].split("\\:")[1];
      points += getWinningPoints(parseLineNumbers(myNumbersAsString), getWinningDict(parseLineNumbers(winningNumbersAsString)));
    }

    return points;
  }

  public static Map<Integer, Boolean> getWinningDict(List<Integer> winningNumbers) {
      Map<Integer, Boolean> winningDict = new HashMap<>();
      for (Integer number : winningNumbers) {
        winningDict.put(number, true);
      }
      return winningDict;
  }

  public static List<Integer> parseLineNumbers(String linePart) {
    return Arrays.stream(linePart.trim().split(" "))
        .filter(Predicate.not(String::isEmpty))
        .mapToInt(s -> Integer.parseInt(s.trim())).boxed().collect(Collectors.toList());
  }

  private static int getWinningPoints(List<Integer> myNumbers, Map<Integer, Boolean> winningNumbers) {
    int points = 0;

    for (Integer number : myNumbers) {
      if (winningNumbers.getOrDefault(number, false)) {
        points = points == 0 ? 1 : points * 2;
      }
    }

    return points;
  }
}
