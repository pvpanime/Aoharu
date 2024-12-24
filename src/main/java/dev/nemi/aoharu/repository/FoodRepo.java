package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepo extends JpaRepository<Food, Long>, FoodSearch {
}
