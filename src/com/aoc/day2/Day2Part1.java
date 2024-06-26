package com.aoc.day2;

import java.util.ArrayList;
import java.util.List;

import com.aoc.Day;
import com.aoc.api.AdventOfCodeApi;

enum Color {
  RED(12), GREEN(13), BLUE(14);

  int maxCount;

  Color(int maxCount) {
    this.maxCount = maxCount;
  }
}

public class Day2Part1 implements Day {

  private AdventOfCodeApi api;

  public Day2Part1(AdventOfCodeApi api) {
    this.api = api;
  }

  public void solve() {
    String[] splitInput = api.getInput(2023, 2);

    List<Game> possibleGames = new ArrayList<Game>();

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
