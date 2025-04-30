package dev.rahulmg.snipshare.controller;

import dev.rahulmg.snipshare.dto.PasteRequest;
import dev.rahulmg.snipshare.exception.custom.PasteNotFoundException;
import dev.rahulmg.snipshare.model.Paste;
import dev.rahulmg.snipshare.service.PasteService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class PasteController {

  private final PasteService pasteService;

  @Autowired
  public PasteController(final PasteService pasteService) {
    this.pasteService = pasteService;
  }

  @GetMapping("/")
  public String homePage(final Model model) {
    if (!model.containsAttribute("pasteRequest")) {
      model.addAttribute("pasteRequest", new PasteRequest());
    }
    return "home";
  }

  @PostMapping("/paste")
  public String createPaste(@Valid @ModelAttribute("pasteRequest") final PasteRequest pasteRequest,
                            final BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      return "home";
    }

    final Paste paste = pasteService.createPaste(
      pasteRequest.getContent(),
      pasteRequest.getExpirationMinutes()
    );
    return "redirect:/" + paste.getShortUrl();
  }

  @GetMapping("/{shortUrl}")
  public String viewPaste(@PathVariable final String shortUrl, final Model model, final HttpServletRequest request) {
    final Optional<Paste> pasteOptional = pasteService.getPasteByShortUrl(shortUrl);

    if (pasteOptional.isEmpty()) {
      throw new PasteNotFoundException("Paste not found or has expired");
    }

    model.addAttribute("paste", pasteOptional.get());
    model.addAttribute("httpServletRequest", request);
    return "view";
  }
}
