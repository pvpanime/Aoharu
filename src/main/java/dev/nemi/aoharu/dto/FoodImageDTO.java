package dev.nemi.aoharu.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FoodImageDTO {
  private String id;
  private String name;
  private int ordinal;
}
