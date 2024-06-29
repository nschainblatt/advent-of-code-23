package com.aoc.tests;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

import com.aoc.DayTestFactory;
import com.aoc.Main;

public class MainTest {
  public static void main(String[] args) throws IOException {
    Map<Main.CommandLineArgument, Integer> parsedCommandLineArguments = Main.parseCommandLineArguments(args);

    Optional<DayTest> aocDay = DayTestFactory.createDayTestObject(
        parsedCommandLineArguments.get(Main.CommandLineArgument.Year),
        parsedCommandLineArguments.get(Main.CommandLineArgument.Day),
        parsedCommandLineArguments.get(Main.CommandLineArgument.Part));

    System.out.printf("Year: %d, Day %d, Part: %d\n",
        parsedCommandLineArguments.get(Main.CommandLineArgument.Year),
        parsedCommandLineArguments.get(Main.CommandLineArgument.Day),
        parsedCommandLineArguments.get(Main.CommandLineArgument.Part));
    aocDay.ifPresentOrElse(
        (dayTest) -> dayTest.run(),
        () -> System.out.println("Invalid Year, Day, or Part argument"));
  }
}
