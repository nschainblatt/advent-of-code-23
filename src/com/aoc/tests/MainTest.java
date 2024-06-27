package com.aoc.tests;

import java.io.IOException;

import com.aoc.dotenv.DotEnv;
import com.aoc.api.AdventOfCodeApi;

public class MainTest {
  public static void main(String[] args) throws IOException {
    String sessionCookie = new DotEnv()
        .loadVariables()
        .getVariables()
        .get("sessionCookie");
    AdventOfCodeApi api = new AdventOfCodeApi(sessionCookie);
    new Day3Part1Test(api).run();
  }
}
