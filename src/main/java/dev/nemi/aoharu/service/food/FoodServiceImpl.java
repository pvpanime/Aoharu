package dev.nemi.aoharu.service.food;

import dev.nemi.aoharu.dto.*;
import dev.nemi.aoharu.prime.Food;
import dev.nemi.aoharu.repository.food.FoodRepo;
import dev.nemi.aoharu.repository.food.FoodReviewRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodRepo foodRepo;
  private final FoodReviewRepo foodReviewRepo;
  private final ModelMapper modelMapper;

  @Override
  public PageResponseDTO<FoodViewDTO> getFoods(FoodPageRequestDTO requestDTO) {
    Page<FoodViewDTO> foods = foodRepo.getFoodsImageSupport(
      requestDTO.getPageable("id"),
      requestDTO.getSearchName(),
      requestDTO.getMinPrice(),
      requestDTO.getMaxPrice(),
      requestDTO.getMinRate(),
      requestDTO.getUntil()
    );

    return PageResponseDTO.<FoodViewDTO>withAll()
      .pageRequestDTO(requestDTO)
      .dtoList(foods.getContent())
      .total(foods.getTotalElements())
      .build();
  }

  @Override
  public FoodViewDTO getOne(long id) {
    Food food = foodRepo.getOneWithImages(id).orElseThrow();

    FoodReviewValueDTOI r = foodReviewRepo.getReviewValuesOf(id);
    Long reviewCount = r != null ? r.getReviewCount() : 0L;
    Double avgRate = r != null ? r.getAvgRate() : 0.0;

    return FoodViewDTO.of(food, reviewCount, avgRate);
  }

  @Override
  public Long register(FoodRegisterDTO dto) {
    if (dto.getRegistrar() == null) dto.setRegistrar("hina");
    Food food = entityOf(dto);
    Food result = foodRepo.save(food);
    return result.getId();
  }

  @Override
  public void edit(FoodEditDTO dto) {
    Food food = foodRepo.findById(dto.getId()).orElseThrow();
    food.edit(
      dto.getName(),
      dto.getDescription(),
      dto.getPrice(),
      dto.getStock(),
      dto.getOpened(),
      dto.getClose()
    );

    food.clearImages();
    if (dto.getImageFiles() != null) {
      for (String file : dto.getImageFiles()) {
        String[] chunk = file.split("_");
        food.addImage(chunk[0], chunk[1]);
      }
    }

    foodRepo.save(food);
  }

  @Override
  public void delete(long id) {

    long commentCount = foodReviewRepo.deleteByFood_Id(id);
    log.info("Deleted {} comment(s) for food {}", commentCount, id);

    foodRepo.deleteById(id);

  }

}
