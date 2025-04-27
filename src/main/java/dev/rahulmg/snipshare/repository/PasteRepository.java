package dev.rahulmg.snipshare.repository;

import dev.rahulmg.snipshare.model.Paste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasteRepository extends JpaRepository<Paste, Long> {

  /**
   * Find a paste by its short URL
   *
   * @param shortUrl the short URL of the paste
   * @return an Optional containing the paste if found, or empty if not found
   */
  Optional<Paste> findByShortUrl(String shortUrl);

  /**
   * Check if a paste with the given short URL exists
   *
   * @param shortUrl the short URL to check
   * @return true if a paste with the given short URL exists, false otherwise
   */
  boolean existsByShortUrl(String shortUrl);
}