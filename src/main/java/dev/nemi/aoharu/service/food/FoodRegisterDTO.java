package dev.nemi.aoharu.service.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodRegisterDTO {
  private String name;
  private String description;
  private Long price;
  private Long stock;

  private LocalDateTime opened;
  private LocalDateTime close;
  private String registrar;
}
