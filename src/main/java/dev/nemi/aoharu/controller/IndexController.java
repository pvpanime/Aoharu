package dev.nemi.aoharu.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log4j2
public class IndexController {

  @GetMapping("/")
  public String index() { return "index"; }

  @GetMapping("/arona")
  public String arona() { return "arona"; }

  @GetMapping("/login")
  public String login(String error, String logout) {
    return "login";
  }

}
