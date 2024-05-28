package com.aoc;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.Scanner;

public class AdventOfCodeApi {
  String day;
  String year;
  String sessionCookie;

  public AdventOfCodeApi() {
    this.day = "1";
    this.year = "2023";
    this.sessionCookie = "session=YOUR_SESSION_COOKIE";
  }

  public AdventOfCodeApi(String day) {
    this.day = day;
    this.year = "2023";
    this.sessionCookie = "session=YOUR_SESSION_COOKIE";
  }

  public AdventOfCodeApi(String day, String year) {
    this.day = day;
    this.year = year;
    this.sessionCookie = "session=YOUR_SESSION_COOKIE";
  }

  public AdventOfCodeApi(String day, String year, String sessionCookie) {
    this.day = day;
    this.year = year;
    this.sessionCookie = sessionCookie;
  }

  public String getInput() {
    try {
      HttpClient client = HttpClient.newHttpClient();
      HttpRequest request = HttpRequest.newBuilder()
          .uri(URI.create(String.format("https://adventofcode.com/%s/day/%s/input", this.year, this.day)))
          .header("Cookie",
              "session=YOUR_SESSION_COOKIE")
          .build();
      CompletableFuture<HttpResponse<String>> response = client.sendAsync(request, BodyHandlers.ofString());
      return response.get().body();
    } catch (Exception e) {
      System.out.println("An error occurred getting your input, would you like to view the error? Y/n");
      Scanner scanner = new Scanner(System.in);
      String answer = scanner.nextLine();
      if (answer.trim().isEmpty() || answer.trim().toUpperCase() == "Y") {
        System.out.println(e);
      }
      System.out.println("You may need to update your session cookie");
      return "";
    }
  }
}
