package dev.nemi.aoharu.service;


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
  private String title;
  private String content;
}
