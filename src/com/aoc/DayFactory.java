package com.aoc;

import java.io.IOException;
import java.util.Optional;

import com.aoc.api.AdventOfCodeApi;
import com.aoc.day2.*;
import com.aoc.day3.*;
import com.aoc.day4.Day4Part1;
import com.aoc.dotenv.DotEnv;

// TODO: add year condition

public class DayFactory {
  public static Optional<Day> createDayObject(int year, int day, int part) throws IOException {
    String sessionCookie = new DotEnv()
        .loadVariables()
        .getVariables()
        .get("sessionCookie");
    AdventOfCodeApi api = new AdventOfCodeApi(sessionCookie);
    switch (day) {
      case 1:
        System.out.println("No day 1 yet");
        return Optional.empty();
      case 2:
        if (part == 1) {
          return Optional.ofNullable(new Day2Part1(api));
        } else if (part == 2) {
          return Optional.ofNullable(new Day2Part2(api));
        }
        return Optional.empty();
      case 3:
        if (part == 1) {
          return Optional.ofNullable(new Day3Part1(api));
        } else if (part == 2) {
          return Optional.ofNullable(new Day3Part2(api));
        }
        return Optional.empty();
      case 4:
        if (part == 1) {
          return Optional.ofNullable(new Day4Part1(api));
        }
        return Optional.empty();
    }
    return Optional.empty();
  }
}
