package dev.rahulmg.snipshare.exception;

/**
 * Exception thrown when a requested paste is not found or has expired.
 */
public class PasteNotFoundException extends RuntimeException {

  /**
   * Constructs a new PasteNotFoundException with the specified detail message.
   *
   * @param message the detail message
   */
  public PasteNotFoundException(String message) {
    super(message);
  }
}