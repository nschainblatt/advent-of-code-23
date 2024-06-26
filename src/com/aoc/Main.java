package com.aoc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

enum CommandLineArgument {
  Year, Day, Part
}

public class Main {
  public static final int DEFAULT_YEAR = 2023;
  public static final int DEFAULT_DAY = 3;
  public static final int DEFAULT_PART = 1;

  public static void main(String[] args) throws IOException, DayNotFoundException {
    // args = new String[] {"Day=1", "Year=2024", "Part=1"};
    Map<CommandLineArgument, Integer> parsedCommandLineArguments = parseCommandLineArguments(args);
    Optional<Day> aocDay = DayFactory.createDayObject(
        parsedCommandLineArguments.get(CommandLineArgument.Year),
        parsedCommandLineArguments.get(CommandLineArgument.Day),
        parsedCommandLineArguments.get(CommandLineArgument.Part));
    aocDay.ifPresentOrElse(Day::solve,
    () -> System.out.println("Invalid Year, Day, or Part argument"));
  }

  // Command line arguments are expected to be in format: Day=N, Year=int, Part=N
  // and are optional
  private static Map<CommandLineArgument, Integer> parseCommandLineArguments(String[] args) {
    Map<CommandLineArgument, Integer> parsedCommandLineArguments = new HashMap<>();
    parsedCommandLineArguments.put(CommandLineArgument.Year, DEFAULT_YEAR);
    parsedCommandLineArguments.put(CommandLineArgument.Day, DEFAULT_DAY);
    parsedCommandLineArguments.put(CommandLineArgument.Part, DEFAULT_PART);

    List<String> types = Arrays
        .asList(Arrays.stream(CommandLineArgument.values()).map(Enum::name).toArray(String[]::new));

    for (String arg : args) {
      if (arg.indexOf("=") != -1 && arg.split("=").length > 1) {
        String key = arg.split("=")[0];
        Integer value = null;
        try {
          value = Integer.parseInt(arg.split("=")[1]);
        } catch (NumberFormatException e) {
          System.err.println("Invalid value provided, expected an integer: " + e);
        }
        if (types.contains(key) && value != null) {
          parsedCommandLineArguments.put(CommandLineArgument.valueOf(key), value);
        }
      }
    }

    return parsedCommandLineArguments;
  }
}
