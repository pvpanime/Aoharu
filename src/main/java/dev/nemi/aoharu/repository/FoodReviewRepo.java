package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.FoodReview;
import dev.nemi.aoharu.service.food.FoodRatingGroupProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface FoodReviewRepo extends JpaRepository<FoodReview, Long>, FoodSearch {

  @Query("select f from FoodReview f where f.food.id = :foodId")
  Page<FoodReview> getReviewsOf(Pageable pageable, long foodId);

  @Query("select count(r.id) as reviewCount, avg(r.rating) as avgRate from FoodReview r where r.food.id = :foodId group by r.food")
  FoodRatingGroupProjection getRating(long foodId);

  void deleteByFood_Id(Long foodId);
}
