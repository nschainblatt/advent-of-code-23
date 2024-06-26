package com.aoc.day2;

import java.util.ArrayList;
import java.util.List;

import com.aoc.Day;
import com.aoc.api.AdventOfCodeApi;

public class Day2Part2 implements Day {

  enum Color {
    RED(12), GREEN(13), BLUE(14);

    int maxCount;

    Color(int maxCount) {
      this.maxCount = maxCount;
    }
  }

  private AdventOfCodeApi api;

  public Day2Part2(AdventOfCodeApi api) {
    this.api = api;
  }

  public void solve() {
    String[] splitInput = api.getInput(2023, 2);

    List<Game> games = new ArrayList<Game>();

    for (String line : splitInput) {
      Game newGame = new Game(line);
      newGame.parseLine();
      games.add(newGame);
    }

    int sum = 0;
    for (Game game : games) {
      int power = game.minRedRequired * game.minGreenRequired * game.minBlueRequired;
      sum += power;
    }

    System.out.println(sum);
  }

  static class Game {

    int id;
    String line;
    List<String> rounds;
    int minRedRequired;
    int minGreenRequired;
    int minBlueRequired;

    Game(String line) {
      this.id = Integer.parseInt(line.split(":")[0].split(" ")[1]);
      this.line = line;
      this.rounds = new ArrayList<String>();
    }

    void parseLine() {
      String gameContents = this.line.split(":")[1];
      String gameRounds[] = gameContents.split(";");
      int roundCount = 1;

      for (String round : gameRounds) {
        this.rounds.add("Round " + roundCount + ": " + round.trim());
        String[] colorWithCounts = round.split(",");

        for (String colorWithCount : colorWithCounts) {
          Color color = Color.valueOf(colorWithCount.split(" ")[2].trim().toUpperCase());
          int count = Integer.parseInt(colorWithCount.split(" ")[1].trim());

          if (color == Color.RED && count > this.minRedRequired) {
            this.minRedRequired = count;
          } else if (color == Color.GREEN && count > this.minGreenRequired) {
            this.minGreenRequired = count;
          } else if (color == Color.BLUE && count > this.minBlueRequired) {
            this.minBlueRequired = count;
          }
        }

        roundCount++;
      }
    }

    @Override
    public String toString() {
      return this.rounds.toString();
    }
  }
}
