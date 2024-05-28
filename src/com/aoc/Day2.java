package com.aoc;

import java.util.ArrayList;
import java.util.List;

import com.aoc.AdventOfCodeApi;

public class Day2 {

  enum Color {
    RED(12), GREEN(13), BLUE(14);

    int maxCount;

    Color(int maxCount) {
      this.maxCount = maxCount;
    }
  }

  public static void main(String[] args) {
    AdventOfCodeApi api = new AdventOfCodeApi("2", "2023");
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
          Color color = Color.valueOf(colorWithCount.split(" ")[2].trim().toUpperCase());
          int count = Integer.parseInt(colorWithCount.split(" ")[1].trim());
          if (count > color.maxCount) {
            return false;
          }
        }
      }
      return true;
    }

    @Override
    public String toString() {
      return this.line;
    }
  }
}
