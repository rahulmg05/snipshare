package dev.rahulmg.snipshare.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasteRequest {

  @NotBlank(message = "Content cannot be empty")
  @Size(max = 50000, message = "Content cannot exceed 50000 characters")
  private String content;

  @Min(value = 5, message = "Expiration time must be at least 5 minutes")
  @Max(value = 43200, message = "Expiration time cannot exceed 30 days (43200 minutes)")
  private int expirationMinutes = 1440; // Default to 24 hours
}