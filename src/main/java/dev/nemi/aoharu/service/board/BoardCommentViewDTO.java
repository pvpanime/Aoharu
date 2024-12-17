package dev.nemi.aoharu.service.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentViewDTO {

  @NotNull
  private Long id;

  @NotNull
  private Long boardId;

  @NotBlank
  private String content;

  @NotBlank
  private String reUserid;

  @NotNull
  private LocalDateTime added;

  @NotNull
  private LocalDateTime updated;

}
