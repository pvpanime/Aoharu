package dev.nemi.aoharu;


import dev.nemi.aoharu.prime.Food;
import dev.nemi.aoharu.repository.FoodRepo;
import dev.nemi.aoharu.repository.FoodReviewRepo;
import dev.nemi.aoharu.service.food.FoodService;
import dev.nemi.aoharu.service.food.FoodViewImageSupportDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class FoodTest {

  @Autowired
  private FoodRepo foodRepo;

  @Autowired
  private FoodReviewRepo foodReviewRepo;

  @Autowired
  private FoodService foodService;

//  @Test
//  public void addImageTest() {
//    Food food = foodRepo.getOneWithImages(1L).orElseThrow();
//    food.addImage("d9575130-9e5b-4c31-a6e7-934aa0c92e53", "bob1.png");
//    food.addImage("0edb15e7-ac1e-4cd9-8d78-90f576daff52", "bob2.png");
//    food.addImage("10f6ebc3-6520-416c-a4b6-008f57f366c6", "bob3.png");
//    foodRepo.save(food);
//  }

  @Test
  public void getFoodsWithImageTest() {
    FoodViewImageSupportDTO foodDTO = foodService.getOneWithImage(1L);
    log.info("foodDTO: {}", foodDTO);

    FoodViewImageSupportDTO foodDTO2 = foodService.getOneWithImage(11L);
    log.info("foodDTO2: {}", foodDTO2);
  }

  @Transactional
  @Test
  public void n1Test() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("id"));
    Page<FoodViewImageSupportDTO> dtoPage = foodRepo.getFoodsImageSupport(pageable);
    for (FoodViewImageSupportDTO dto : dtoPage) {
      log.info("dto: {}", dto);
    }
  }

}
