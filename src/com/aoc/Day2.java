package com.aoc;

import java.util.ArrayList;
import java.util.List;

import com.aoc.AdventOfCodeApi;

public class Day2 {

  public static final int RED = 12;
  public static final int GREEN = 13;
  public static final int BLUE = 14;

  public static void main(String[] args) {
    AdventOfCodeApi api = new AdventOfCodeApi();
    String input = api.getInput();

    if (input.isEmpty()) {
      System.out.println("AOC input is empty, check the session cookie");
      return;
    }

    String[] splitInput = input.split("\n");
    List<Game> possibleGames = new ArrayList<Game>();

    // Note, not going to wrap every instance of parseInt in a try catch block.
    // If the input from AOC is received then it will work perfectly.

    for (String line : splitInput) {
      Game newGame = new Game(
          Integer.parseInt(line.split(":")[0].split(" ")[1]),
          line);

      if (newGame.isPossible()) {
        possibleGames.add(newGame);
      }
    }

    int sum = 0;
    for (Game possibleGame : possibleGames) {
      sum += possibleGame.id;
    }
    System.out.println(sum);
  }

  static class Game {

    int id;
    String line;
    int red;
    int green;
    int blue;

    Game(int id, String line) {
      this.id = id;
      this.line = line;
    }

    boolean isPossible() {
      String gameContents = this.line.split(":")[1];
      String gameRounds[] = gameContents.split(";");
      for (String round : gameRounds) {
        String[] colorWithCounts = round.split(",");
        for (String colorWithCount : colorWithCounts) {
          String color = colorWithCount.split(" ")[2].trim();
          int count = Integer.parseInt(colorWithCount.split(" ")[1].trim());
          switch (color) {
            case "red":
              this.red += count;
            case "green":
              this.green += count;
            case "blue":
              this.blue += count;
          }
          if (this.red > Day2.RED || this.green > Day2.GREEN || this.blue > Day2.BLUE) {
            return false;
          }
          this.reset();
        }
      }
      return true;
    }

    void reset() {
      this.red = 0;
      this.green = 0;
      this.blue = 0;
    }

    @Override
    public String toString() {
      return this.line;
    }
  }
}
