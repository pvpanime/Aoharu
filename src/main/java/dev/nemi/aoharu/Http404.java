package dev.nemi.aoharu;

public class Http404 extends RuntimeException {
  public Http404(String message) {
    super(message);
  }
}
