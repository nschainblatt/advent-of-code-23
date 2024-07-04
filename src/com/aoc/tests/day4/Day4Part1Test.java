package com.aoc.tests.day4;

import java.util.List;

import com.aoc.api.AdventOfCodeApi;
import com.aoc.day4.Day4Part1;
import com.aoc.tests.DayTest;

public class Day4Part1Test implements DayTest {
    private static final String exampleInput = """
            Card 1: 41 48 83 86 17 | 83 86  6 31 17  9 48 53
            Card 2: 13 32 20 16 61 | 61 30 68 82 17 32 24 19
            Card 3:  1 21 53 59 44 | 69 82 63 72 16 21 14  1
            Card 4: 41 92 73 84 69 | 59 84 76 51 58  5 54 83
            Card 5: 87 83 26 28 32 | 88 30 70 12 93 22 82 36
            Card 6: 31 18 13 56 72 | 74 77 10 23 35 67 36 11
            """;
    private AdventOfCodeApi api;

    public Day4Part1Test(AdventOfCodeApi api) {
        this.api = api;
    }

    public void run() {
        System.out.println("Running tests for day 4 part 1");
        System.out.printf("Test 1: Day 4 example test: %s\n", day4ExampleTest() ? "PASSED" : "FAILED");
        System.out.printf("Test 2: Day 4 number parsed test: %s\n", numberParserTest() ? "PASSED" : "FAILED");
        System.out.printf("Test 3: Day 4 timed input test: %s\n", day4Part1InputTest() ? "PASSED" : "FAILED");
    }

    private boolean day4ExampleTest() {
        Day4Part1 day4Part1 = new Day4Part1();
        int answer = day4Part1.solve(exampleInput.split("\n"));
        System.out.printf("Result should equal: %d. Result equals: %d\n", 13, answer);
        return answer == 13;
    }

    private boolean numberParserTest() {
        List<List<Integer>> winningNumbers = List.of(
                List.of(41, 48, 83, 86, 17),
                List.of(13, 32, 20, 16, 61),
                List.of(1, 21, 53, 59, 44),
                List.of(41, 92, 73, 84, 69),
                List.of(87, 83, 26, 28, 32),
                List.of(31, 18, 13, 56, 72));

        List<List<Integer>> myNumbers = List.of(
                List.of(83, 86, 6, 31, 17, 9, 48, 53),
                List.of(61, 30, 68, 82, 17, 32, 24, 19),
                List.of(69, 82, 63, 72, 16, 21, 14, 1),
                List.of(59, 84, 76, 51, 58, 5, 54, 83),
                List.of(88, 30, 70, 12, 93, 22, 82, 36),
                List.of(74, 77, 10, 23, 35, 67, 36, 11));

        String[] input = exampleInput.split("\n");
        for (int i = 0; i < input.length; i++) {
            String[] linePart = input[i].split("\\|");
            String myNumbersAsString = linePart[1];
            String winningNumbersAsString = linePart[0].split("\\:")[1];
            List<Integer> myNumbersTest = Day4Part1.parseLineNumbers(myNumbersAsString);
            List<Integer> winningNumbersTest = Day4Part1.parseLineNumbers(winningNumbersAsString);
            if (!winningNumbers.get(i).equals(winningNumbersTest) || !myNumbers.get(i).equals(myNumbersTest)) {
                return false;
            }
        }

        return true;
    }

    private boolean day4Part1InputTest() {
        long startTime = System.nanoTime();
        String[] input = api.getInput(2023, 4);
        Day4Part1 day4Part1 = new Day4Part1();
        int answer = day4Part1.solve(input);
        long endTime = System.nanoTime() - startTime;
        System.out.printf("Day 4 part 1 took: %f seconds\n", ((double) endTime) / 1_000_000_000);
        return answer == 17782;
    }
}
