package com.aoc.api;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.Scanner;

public class AdventOfCodeApi {
  private String sessionCookie;

  public AdventOfCodeApi(String sessionCookie) {
    this.sessionCookie = sessionCookie;
  }

  public String[] getInput(int year, int day) {
    try {
      URI uri = URI.create(String.format("https://adventofcode.com/%d/day/%d/input", year, day));
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(uri)
          .header("Cookie", sessionCookie)
          .build();
      HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
      String[] input = response.body().split("\n");
      if (input.length == 0) {
        System.out.println("Input is empty");
      }
      return input;
    } catch (Exception e) {
      System.out.println("An error occurred getting your input, would you like to view the error? (Y/n)");
      try (Scanner scanner = new Scanner(System.in)) {
        String answer = scanner.nextLine();
        if (answer.trim().isEmpty() || answer.trim().toUpperCase() == "Y") {
          System.out.println(e);
        }
      }
      System.out.println("There was a problem getting your input, you may need to update your session cookie");
      return new String[] {};
    }
  }
}
