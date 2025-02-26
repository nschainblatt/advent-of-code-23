package com.aoc;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import com.aoc.api.AdventOfCodeApi;
import com.aoc.dotenv.DotEnv;

public class Main {
  public static enum CommandLineArgument {
    Year, Day, Part
  }

  public static final int DEFAULT_YEAR = 2023;
  public static final int DEFAULT_DAY = 4;
  public static final int DEFAULT_PART = 2;

  public static void main(String[] args) throws IOException, DayNotFoundException {
    // args = new String[] {"Day=1", "Year=2024", "Part=1"};
    Map<CommandLineArgument, Integer> parsedCommandLineArguments = parseCommandLineArguments(args);
    Optional<Day> aocDay = DayFactory.createDayObject(
        parsedCommandLineArguments.get(CommandLineArgument.Year),
        parsedCommandLineArguments.get(CommandLineArgument.Day),
        parsedCommandLineArguments.get(CommandLineArgument.Part));

    System.out.printf("Year: %d, Day %d, Part: %d\n",
        parsedCommandLineArguments.get(CommandLineArgument.Year),
        parsedCommandLineArguments.get(CommandLineArgument.Day),
        parsedCommandLineArguments.get(CommandLineArgument.Part));

    String sessionCookie = new DotEnv()
        .loadVariables()
        .getVariables()
        .get("sessionCookie");
    AdventOfCodeApi api = new AdventOfCodeApi(sessionCookie);
    String[] input = api.getInput(
        parsedCommandLineArguments.get(CommandLineArgument.Year),
        parsedCommandLineArguments.get(CommandLineArgument.Day)
    );
    aocDay.ifPresentOrElse(
        (day) -> System.out.printf("Result: %d\n", day.solve(input)),
        () -> System.out.println("Invalid Year, Day, or Part argument"));
  }

  // Command line arguments are expected to be in format: Day=N, Year=int, Part=N
  // and are optional
  public static Map<CommandLineArgument, Integer> parseCommandLineArguments(String[] args) {
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
