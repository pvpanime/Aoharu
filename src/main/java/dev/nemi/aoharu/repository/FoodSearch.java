package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.service.food.FoodViewDTO;
import dev.nemi.aoharu.service.food.FoodViewImageSupportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FoodSearch {

  Page<FoodViewDTO> getFoods(Pageable pageable, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until);

  Page<FoodViewImageSupportDTO> getFoodsImageSupport(Pageable pgb, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until);
  default Page<FoodViewImageSupportDTO> getFoodsImageSupport(Pageable pgb) {
    return getFoodsImageSupport(pgb, null, null, null, null, null);
  }
}
