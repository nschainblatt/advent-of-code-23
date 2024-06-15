package com.aoc.dotenv;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;
import java.nio.file.Paths;

public class DotEnv {
  public static void main(String[] args) throws IOException {
    // Sample implementation
    DotEnv dotEnv = new DotEnv();
    dotEnv.loadVariables();
    Map<String, String> variables = dotEnv.getVariables();
  }

  private Map<String, String> variables = new HashMap<>();

  public void loadVariables() throws IOException {
    // .env is in project root, and pwd is target when running program via run
    // script
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
  }

  public Map<String, String> getVariables() {
    return this.variables;
  }
}
