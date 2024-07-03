package com.aoc;

import java.io.IOException;
import java.util.Optional;

import com.aoc.api.AdventOfCodeApi;
import com.aoc.dotenv.DotEnv;
import com.aoc.tests.day3.*;
import com.aoc.tests.day4.Day4Part1Test;
import com.aoc.tests.DayTest;

// TODO: add year condition

public class DayTestFactory {
  public static Optional<DayTest> createDayTestObject(int year, int day, int part) throws IOException {
    String sessionCookie = new DotEnv()
        .loadVariables()
        .getVariables()
        .get("sessionCookie");
    AdventOfCodeApi api = new AdventOfCodeApi(sessionCookie);
    switch (day) {
      case 1:
        System.out.println("No tests for day 1");
        return Optional.empty();
      case 2:
        System.out.println("No tests for day 2");
        return Optional.empty();
      case 3:
        if (part == 1) {
          return Optional.ofNullable(new Day3Part1Test(api));
        } else if (part == 2) {
          return Optional.ofNullable(new Day3Part2Test(api));
        }
        return Optional.empty();
      case 4:
        if (part == 1) {
          return Optional.ofNullable(new Day4Part1Test(api));
        }
        return Optional.empty();
    }
    return Optional.empty();
  }
}
