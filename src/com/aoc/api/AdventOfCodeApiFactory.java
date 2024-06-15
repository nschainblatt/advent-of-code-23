package com.aoc.api;

import java.io.IOException;

import com.aoc.dotenv.DotEnv;

public class AdventOfCodeApiFactory {
  DotEnv dotEnv;

  public AdventOfCodeApiFactory(DotEnv dotEnv) {
    this.dotEnv = dotEnv;
  }
  
  public AdventOfCodeApi createApiInstance(int day, int year) throws IOException {
    this.dotEnv.loadVariables();
    String sessionCookie = dotEnv.getVariables().get("sessionCookie");
    AdventOfCodeApi api = new AdventOfCodeApi(day, year, sessionCookie);
    return api;
  }
}
