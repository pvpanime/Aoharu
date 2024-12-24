package dev.nemi.aoharu.service.food;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodViewImageSupportDTO {
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
  private List<FoodImageDTO> images;
}
