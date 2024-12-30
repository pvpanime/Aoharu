package dev.nemi.aoharu.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@Log4j2
public class IndexController {

  @GetMapping("/")
  public String index(Model model) {
    model.addAttribute("name", "Hello World!");
    model.addAttribute("list", new String[] { "The", "Quick", "Brown", "Fox", "Jumps", "Over" });
    return "index";
  }

  @GetMapping("/arona")
  public void arona() { }

  @GetMapping("/list")
  public void list(Model model) {
    model.addAttribute("ls", List.of());
  }

  @GetMapping("/login")
  public String login(String error, String logout) {

    return "login";
  }

}
