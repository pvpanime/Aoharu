package dev.nemi.aoharu.service.board;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BoardListViewDTO {
  private Long bid;
  private String title;
  private String userid;
  private LocalDateTime added;
  private Long commentCount;
}
