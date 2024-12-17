package dev.nemi.aoharu.service.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardCommentWriteDTO {

  @NotNull
  private Long boardId;

  @NotBlank
  private String content;

  @NotBlank
  private String userid;
}
