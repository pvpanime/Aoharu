package dev.nemi.aoharu.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Log4j2
@RestController
public class ReflectRestController {

  @GetMapping(value = "/json", produces = "application/json")
  public Object json(@RequestParam MultiValueMap<String, String> allParams) {
    log.info("Received query params: {}", allParams);

    Map<String, Object> result = new HashMap<>();
    for (Map.Entry<String, List<String>> entry : allParams.entrySet()) {
      List<String> values = entry.getValue();
      result.put(entry.getKey(), values.size() == 1 ? values.get(0) : values);
    }

    return result;
  }

  @GetMapping("/bread")
  public Object bread() {
    return Map.of("bread", "brioche");
  }

  @PostMapping("/bread")
  public Object breadPost() {
//    log.info("Received body: {}", body);
    return Map.ofEntries(
      Map.entry("success", Boolean.TRUE),
      Map.entry("errors", false),
      Map.entry("message", "I got this bread! thanks!")
    );
  }
}
