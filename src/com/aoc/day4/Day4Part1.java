package com.aoc.day4;

import java.util.Arrays;
import java.util.List;
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
      points += getWinningPoints(parseLineNumbers(myNumbersAsString), parseLineNumbers(winningNumbersAsString));
    }

    return points;
  }

  public static List<Integer> parseLineNumbers(String linePart) {
    return Arrays.stream(linePart.trim().split(" "))
        .filter(Predicate.not(String::isEmpty))
        .mapToInt(s -> Integer.parseInt(s.trim())).boxed().collect(Collectors.toList());
  }

  private static int getWinningPoints(List<Integer> myNumbers, List<Integer> winningNumbers) {
    int points = 0;

    for (Integer winningNumber : winningNumbers) {
      if (myNumbers.contains(winningNumber)) {
        points = points == 0 ? 1 : points * 2;
      }
    }

    return points;
  }
}
