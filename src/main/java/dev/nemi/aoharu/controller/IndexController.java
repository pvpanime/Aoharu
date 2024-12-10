package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.ThymeDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@Log4j2
public class IndexController {

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("name", "Hello World!");
    model.addAttribute("list", new String[] { "The", "Quick", "Brown", "Fox", "Jumps", "Over" });
    model.addAttribute("dto", ThymeDTO.builder().scalar(42).message("The Universe").chronal(LocalDateTime.now()).build());
    return "index";
  }

  @GetMapping("/hello")
  public ResponseEntity<String> hello() {
    return ResponseEntity.ok().contentType(MediaType.TEXT_PLAIN).body("Hello!");
  }

  @GetMapping("/arona")
  public void arona(Model model) {

  }

  @GetMapping("/array")
  public String useArray() {
    return "array";
  }

  @GetMapping("/list")
  public void list(Model model) {
    model.addAttribute("ls", List.of(
      new ThymeDTO(10, "Deca", LocalDateTime.now()),
      new ThymeDTO(2, "22", LocalDateTime.of(2222, 2, 22, 22, 22)),
      new ThymeDTO(35, "70 / 2", LocalDateTime.of(2025, 1, 30, 12, 15))
    ));
  }

  @GetMapping("/register")
  public void register() {}

}
