package com.aoc;

import com.aoc.AdventOfCodeApi;

public class Day2 {
  public static void main(String[] args) {
    AdventOfCodeApi api = new AdventOfCodeApi();
    String input = api.getInput();
    String[] splitInput = input.split("\n");
    System.out.println(splitInput[0]);
  }
}
