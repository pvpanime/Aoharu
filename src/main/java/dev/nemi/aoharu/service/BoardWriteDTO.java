package dev.nemi.aoharu.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardWriteDTO {
  private Long id;
  private String title;
  private String content;
  private String userid;

  @Builder.Default
  private Integer status = 1;
  private LocalDateTime added;
  private LocalDateTime updated;
}
