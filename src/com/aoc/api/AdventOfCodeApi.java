package com.aoc.api;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.Scanner;

public class AdventOfCodeApi {
  private int year;
  private int day;
  private String sessionCookie;

  AdventOfCodeApi(int year, int day, String sessionCookie) {
    this.year = year;
    this.day = day;
    this.sessionCookie = sessionCookie;
  }

  public String[] getInput() {
    try {
      URI uri = URI.create(String.format("https://adventofcode.com/%d/day/%d/input", this.year, this.day));
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(uri)
          .header("Cookie", sessionCookie)
          .build();
      CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString());
      return response.get().body().split("\n");
    } catch (Exception e) {
      System.out.println("An error occurred getting your input, would you like to view the error? (Y/n)");
      try (Scanner scanner = new Scanner(System.in)) {
        String answer = scanner.nextLine();
        if (answer.trim().isEmpty() || answer.trim().toUpperCase() == "Y") {
          System.out.println(e);
        }
      }
      System.out.println("You may need to update your session cookie");
      return new String[] {};
    }
  }
}
