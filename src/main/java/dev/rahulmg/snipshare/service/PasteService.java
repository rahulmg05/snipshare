package dev.rahulmg.snipshare.service;

import dev.rahulmg.snipshare.model.Paste;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PasteService {

  /**
   * Create a new paste
   *
   * @param content           the content of the paste
   * @param expirationMinutes the number of minutes until the paste expires
   * @return the created paste with generated short URL
   */
  Paste createPaste(final String content, final int expirationMinutes);

  /**
   * Get a paste by its short URL if it exists and has not expired
   *
   * @param shortUrl the short URL of the paste
   * @return an Optional containing the paste if found and not expired, or empty otherwise
   */
  Optional<Paste> getPasteByShortUrl(final String shortUrl);

  /**
   * Generate a unique short URL for a paste
   *
   * @return a unique short URL
   */
  String generateUniqueShortUrl();

  /**
   * Calculate the expiration date based on the current time and expiration minutes
   *
   * @param expirationMinutes the number of minutes until the paste expires
   * @return the calculated expiration date
   */
  LocalDateTime calculateExpirationDate(final int expirationMinutes);
}
