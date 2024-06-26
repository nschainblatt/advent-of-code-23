package com.aoc.dotenv;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Paths;

public class DotEnv {
  // Sample implementation
  public static void main(String[] args) throws IOException {
    Map<String, String> variables = new DotEnv()
      .loadVariables()
      .getVariables();
    // variables.get("variable_name");
  }

  private Map<String, String> variables = new HashMap<>();

  public Map<String, String> getVariables() {
    return this.variables;
  }

  // Assumes a .env file exists in project root
  public DotEnv loadVariables() throws IOException {
    String envPath = Paths.get("").toAbsolutePath().toString() + "/../.env";
    File env = new File(envPath);

    if (!env.exists()) {
      throw new IOException("No environment file found");
    }

    try (Scanner scanner = new Scanner(env)) {
      while (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (line.isEmpty()) {
          continue;
        }
        if (line.contains("=")) {
          String[] keyValueArray = line.split("=", 2);
          String key = keyValueArray[0];
          String value = keyValueArray[1];
          variables.put(key, value);
        } else {
          throw new IllegalArgumentException("Invalid .env format");
        }
      }
    }
    return this;
  }

}
