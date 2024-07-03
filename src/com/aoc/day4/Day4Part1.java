package com.aoc.day4;

import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.aoc.Day;
import com.aoc.api.AdventOfCodeApi;

public class Day4Part1 implements Day {

  // TODO: Fix Day interface and update other days
  public Day4Part1(AdventOfCodeApi api) {
  }
  public int solve() {
    return 0;
  }
  //

  public int solve(String[] input) {
    int sum = 0;

    // The card will be index + 1

    processInput(input);

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
}
