package dev.rahulmg.snipshare.exception;

/**
 * Exception thrown when paste content validation fails.
 */
public class ContentValidationException extends RuntimeException {

  /**
   * Constructs a new ContentValidationException with the specified detail message.
   *
   * @param message the detail message
   */
  public ContentValidationException(String message) {
    super(message);
  }
}