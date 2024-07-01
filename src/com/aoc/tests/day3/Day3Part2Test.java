package com.aoc.tests.day3;

import com.aoc.api.AdventOfCodeApi;
import com.aoc.day3.Day3Part2;
import com.aoc.tests.DayTest;

public class Day3Part2Test implements DayTest {
  private AdventOfCodeApi api;

  public Day3Part2Test(AdventOfCodeApi api) {
    this.api = api;
  }

  public void run() {
    System.out.println("Running tests for day 3 part 2:");
    System.out.printf("Test 1: Day 3 part 2 input test: %s\n", day3Part2InputTest() ? "PASSED" : "FAILED");
    System.out.println();
  }

  private boolean day3Part2InputTest() {
    long startTime = System.nanoTime();
    int result = new Day3Part2(this.api).solve();
    double duration = Long.valueOf((System.nanoTime() - startTime)).doubleValue() / 1_000_000_000;
    System.out.printf("Day 3 part 2 optimized took: %f seconds\n", duration);
    System.out.printf("Test result: %d should equal: %d\n", result, 509115);
    return 75220503 == result;
  }
}
