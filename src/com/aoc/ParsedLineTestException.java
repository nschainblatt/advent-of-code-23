package com.aoc;

public class ParsedLineTestException extends Exception {
  String message;
  public ParsedLineTestException(String message) {
    super();
    this.message = message;
    System.out.println(message);
  }
}
