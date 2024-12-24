package dev.nemi.aoharu.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import dev.nemi.aoharu.prime.Food;
import dev.nemi.aoharu.prime.QFood;
import dev.nemi.aoharu.prime.QFoodReview;
import dev.nemi.aoharu.service.food.FoodImageDTO;
import dev.nemi.aoharu.service.food.FoodViewDTO;
import dev.nemi.aoharu.service.food.FoodViewImageSupportDTO;
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
    if (searchName != null && !searchName.isEmpty())
      booleanBuilder.and(food.name.contains(searchName));

    if (until != null)
      booleanBuilder.and(food.close.goe(until));

    if (minPrice != null)
      booleanBuilder.and(food.price.goe(minPrice));

    if (maxPrice != null)
      booleanBuilder.and(food.price.goe(maxPrice));

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

    return new PageImpl<>(list, pageable, count);
  }

  @Override
  public Page<FoodViewImageSupportDTO> getFoodsImageSupport(Pageable pgb, String searchName, Long minPrice, Long maxPrice, Integer minRate, LocalDateTime until) {

    QFood food = QFood.food;
    QFoodReview foodReview = QFoodReview.foodReview;

    JPQLQuery<Food> foodJPQLQuery = from(food);
    foodJPQLQuery.leftJoin(foodReview).on(foodReview.food.eq(food));

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (searchName != null && !searchName.isEmpty())
      booleanBuilder.and(food.name.contains(searchName));

    if (until != null)
      booleanBuilder.and(food.close.goe(until));

    if (minPrice != null)
      booleanBuilder.and(food.price.goe(minPrice));

    if (maxPrice != null)
      booleanBuilder.and(food.price.goe(maxPrice));

    foodJPQLQuery.where(booleanBuilder);


    foodJPQLQuery.groupBy(food);

    this.getQuerydsl().applyPagination(pgb, foodJPQLQuery);

    JPQLQuery<Tuple> tupleJPQLQuery = foodJPQLQuery.select(food, foodReview.countDistinct(), foodReview.rating.avg());

    List<Tuple> tuples = tupleJPQLQuery.fetch();

    List<FoodViewImageSupportDTO> dtoList = tuples.stream().map(tuple -> {
      Food food1 = tuple.get(food);
      long reviewCount = tuple.get(1, Long.class);
      Double avgRate = tuple.get(2, Double.class);

      FoodViewImageSupportDTO dto = FoodViewImageSupportDTO.builder()
        .id(food1.getId())
        .name(food1.getName())
        .description(food1.getDescription())
        .price(food1.getPrice())
        .stock(food1.getStock())
        .opened(food1.getOpened())
        .close(food1.getClose())
        .registrar(food1.getRegistrar())
        .added(food1.getAdded())
        .updated(food1.getUpdated())
        .reviewCount(reviewCount)
        .avgRate(avgRate)
        .build();

      List<FoodImageDTO> imageList = food1.getImages().stream().sorted().map(
        im -> FoodImageDTO.builder()
          .id(im.getId()).name(im.getFilename()).ordinal(im.getOrdinal()).build()
      ).toList();

      dto.setImages(imageList);

      return dto;
    }).toList();

    long totalCount = tupleJPQLQuery.fetchCount();
    return new PageImpl<>(dtoList, pgb, totalCount);
  }
}
