package dev.nemi.aoharu.service.food;


public interface FoodRatingGroupProjection {
  Long getReviewCount();
  Double getAvgRate();
}
