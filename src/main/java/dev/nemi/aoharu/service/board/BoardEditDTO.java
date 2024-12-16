package dev.nemi.aoharu.service.board;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardEditDTO {
  private Long id;

  @NotBlank
  private String title;

  @NotBlank
  private String content;
}
