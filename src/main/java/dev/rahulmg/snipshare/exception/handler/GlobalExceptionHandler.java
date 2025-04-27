package dev.rahulmg.snipshare.exception.handler;

import dev.rahulmg.snipshare.exception.ContentValidationException;
import dev.rahulmg.snipshare.exception.PasteNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Global exception handler for the application.
 * Centralizes exception handling across all controllers.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

  /**
   * Handles ContentValidationException.
   *
   * @param ex      the exception
   * @param model   the model
   * @param request the HTTP request
   * @return the error view name
   */
  @ExceptionHandler(ContentValidationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public String handleContentValidationException(ContentValidationException ex, Model model, HttpServletRequest request) {
    model.addAttribute("error", ex.getMessage());
    model.addAttribute("status", HttpStatus.BAD_REQUEST.value());
    model.addAttribute("httpServletRequest", request);
    return "error";
  }

  /**
   * Handles PasteNotFoundException.
   *
   * @param ex      the exception
   * @param model   the model
   * @param request the HTTP request
   * @return the error view name
   */
  @ExceptionHandler(PasteNotFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public String handlePasteNotFoundException(PasteNotFoundException ex, Model model, HttpServletRequest request) {
    model.addAttribute("error", ex.getMessage());
    model.addAttribute("status", HttpStatus.NOT_FOUND.value());
    model.addAttribute("httpServletRequest", request);
    return "error";
  }

  /**
   * Handles ResponseStatusException.
   *
   * @param ex      the exception
   * @param model   the model
   * @param request the HTTP request
   * @return the error view name
   */
  @ExceptionHandler(ResponseStatusException.class)
  public String handleResponseStatusException(ResponseStatusException ex, Model model, HttpServletRequest request) {
    model.addAttribute("error", ex.getReason());
    model.addAttribute("status", ex.getStatusCode().value());
    model.addAttribute("httpServletRequest", request);
    return "error";
  }

  /**
   * Handles all other exceptions.
   *
   * @param ex      the exception
   * @param model   the model
   * @param request the HTTP request
   * @return the error view name
   */
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public String handleGenericException(Exception ex, Model model, HttpServletRequest request) {
    model.addAttribute("error", "An unexpected error occurred: " + ex.getMessage());
    model.addAttribute("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
    model.addAttribute("httpServletRequest", request);
    return "error";
  }
}