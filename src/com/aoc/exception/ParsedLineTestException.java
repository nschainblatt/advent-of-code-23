package com.aoc.exception;

public class ParsedLineTestException extends RuntimeException {
  String message;
  public ParsedLineTestException(String message) {
    super();
    this.message = message;
    System.out.println(message);
  }
}
