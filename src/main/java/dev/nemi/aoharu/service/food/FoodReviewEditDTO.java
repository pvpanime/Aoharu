package dev.nemi.aoharu.service.food;

import lombok.Data;

@Data
public class FoodReviewEditDTO {
  private Long id;
  private String comment;
  private Integer rating;
}
