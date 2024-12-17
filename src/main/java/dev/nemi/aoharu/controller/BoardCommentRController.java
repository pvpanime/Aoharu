package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.service.board.BoardCommentWriteDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/comment")
@Log4j2
@RequiredArgsConstructor
public class BoardCommentRController {

  @Tag(name = "add comment")
  @PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Map<String, Long>> add(
    @Valid @RequestBody BoardCommentWriteDTO commentDTO,
    BindingResult bindingResult
  ) throws BindException {
    log.info("Received comment: {}", commentDTO);
    if (bindingResult.hasErrors()) {
      throw new BindException(bindingResult);
    }
    Map<String, Long> responseBody = Map.of("commentId", 123456L, "success", 1L);
    return ResponseEntity.ok(responseBody);
  }

}
