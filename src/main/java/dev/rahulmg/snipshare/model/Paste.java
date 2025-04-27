package dev.rahulmg.snipshare.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "pastes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Paste {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String shortUrl;

  @NotBlank(message = "Content cannot be empty")
  @Size(max = 50000, message = "Content cannot exceed 50000 characters")
  @Column(length = 50000, nullable = false)
  private String content;

  @Column(nullable = false)
  private LocalDateTime expirationDate;

  @CreationTimestamp
  @Column(updatable = false)
  private LocalDateTime createdAt;

  public boolean isExpired() {
    return LocalDateTime.now().isAfter(expirationDate);
  }
}