package dev.nemi.aoharu.repository;

import dev.nemi.pho.service.FoodViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

public interface FoodSearch {

  Page<FoodViewDTO> getFoods(Pageable pageable, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until);
}
