package dev.nemi.aoharu.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import dev.nemi.pho.domain.Food;
import dev.nemi.pho.domain.QFood;
import dev.nemi.pho.domain.QFoodReview;
import dev.nemi.pho.service.FoodViewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.time.LocalDateTime;
import java.util.List;

public class FoodSearchImpl extends QuerydslRepositorySupport implements FoodSearch {

  public FoodSearchImpl() { super(Food.class); }

  @Override
  public Page<FoodViewDTO> getFoods(Pageable pageable, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until) {

    QFood food = QFood.food;
    QFoodReview foodReview = QFoodReview.foodReview;
    JPQLQuery<Food> query = from(food);

    query.leftJoin(foodReview).on(foodReview.food.eq(food));
    query.groupBy(food);

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (searchName != null && !searchName.isEmpty()) {
      booleanBuilder.and(food.name.contains(searchName));
    }

    if (until != null) {
      booleanBuilder.and(food.close.goe(until));
    }

    if (minPrice != null) {
      booleanBuilder.and(food.price.goe(minPrice));
    }

    if (maxPrice != null) {
      booleanBuilder.and(food.price.goe(maxPrice));
    }

    query.where(booleanBuilder);

    JPQLQuery<FoodViewDTO> bigQuery = query.select(
      Projections.bean(
        FoodViewDTO.class,
        food.id,
        food.name,
        food.description,
        food.price,
        food.stock,
        food.opened,
        food.close,
        food.registrar,
        food.added,
        food.updated,
        foodReview.count().as("reviewCount"),
        foodReview.rating.avg().as("avgRate")
      )
    );

    if (minRate != null) {
      bigQuery.having(foodReview.rating.avg().goe(minRate));
    }

    this.getQuerydsl().applyPagination(pageable, bigQuery);

    List<FoodViewDTO> list = bigQuery.fetch();
    long count = bigQuery.fetchCount();

//    return null;
    return new PageImpl<>(list, pageable, count);
  }
}
