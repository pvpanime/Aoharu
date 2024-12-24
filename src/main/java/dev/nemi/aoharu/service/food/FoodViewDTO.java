package dev.nemi.aoharu.service.food;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FoodViewDTO {
  private Long id;
  private String name;
  private String description;
  private Long price;
  private Long stock;
  private LocalDateTime opened;
  private LocalDateTime close;
  private String registrar;
  private LocalDateTime added;
  private LocalDateTime updated;
  private Long reviewCount;
  private Double avgRate;
}
