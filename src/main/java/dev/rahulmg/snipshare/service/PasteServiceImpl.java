package dev.rahulmg.snipshare.service;

import dev.rahulmg.snipshare.exception.custom.ContentValidationException;
import dev.rahulmg.snipshare.model.Paste;
import dev.rahulmg.snipshare.repository.PasteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PasteServiceImpl implements PasteService {

  private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int SHORT_URL_LENGTH = 8;
  private final PasteRepository pasteRepository;
  private final int maxContentLength;
  private final SecureRandom random = new SecureRandom();

  @Autowired
  public PasteServiceImpl(final PasteRepository pasteRepository,
                          @Value("${snipshare.max-content-length}") final int maxContentLength) {
    this.pasteRepository = pasteRepository;
    this.maxContentLength = maxContentLength;
  }

  @Override
  public Paste createPaste(final String content, final int expirationMinutes) {
    if (content == null || content.isBlank()) {
      throw new ContentValidationException("Content cannot be empty");
    }

    if (content.length() > maxContentLength) {
      throw new ContentValidationException("Content cannot exceed " + maxContentLength + " characters");
    }

    final Paste paste = new Paste();
    paste.setContent(content);
    paste.setShortUrl(generateUniqueShortUrl());
    paste.setExpirationDate(calculateExpirationDate(expirationMinutes));

    return pasteRepository.save(paste);
  }

  @Override
  public Optional<Paste> getPasteByShortUrl(final String shortUrl) {
    final Optional<Paste> pasteOptional = pasteRepository.findByShortUrl(shortUrl);

    if (pasteOptional.isPresent()) {
      final Paste paste = pasteOptional.get();
      if (paste.isExpired()) {
        return Optional.empty();
      }
      return Optional.of(paste);
    }

    return Optional.empty();
  }

  @Override
  public String generateUniqueShortUrl() {
    while (true) {
      final String shortUrl = generateRandomShortUrl();
      if (!pasteRepository.existsByShortUrl(shortUrl)) {
        return shortUrl;
      }
    }
  }

  @Override
  public LocalDateTime calculateExpirationDate(final int expirationMinutes) {
    if (expirationMinutes <= 0) {
      // Default to 24 hours if no valid expiration is provided
      final int defaultExpirationMinutes = 24 * 60;
      return LocalDateTime.now().plusMinutes(defaultExpirationMinutes);
    }
    return LocalDateTime.now().plusMinutes(expirationMinutes);
  }

  private String generateRandomShortUrl() {
    final StringBuilder sb = new StringBuilder(SHORT_URL_LENGTH);
    generateRandomChars(sb);
    return sb.toString();
  }

  private void generateRandomChars(final StringBuilder sb) {
    java.util.stream.IntStream.range(0, SHORT_URL_LENGTH).forEach(i -> {
      final int randomIndex = random.nextInt(ALPHANUMERIC.length());
      sb.append(ALPHANUMERIC.charAt(randomIndex));
    });
  }
}
