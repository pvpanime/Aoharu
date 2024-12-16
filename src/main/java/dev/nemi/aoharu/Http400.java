package dev.nemi.aoharu;

public class Http400 extends RuntimeException {
  public Http400(String message) {
    super(message);
  }
}
