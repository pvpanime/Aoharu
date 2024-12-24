package dev.nemi.aoharu.service.food;

import dev.nemi.pho.domain.Food;
import dev.nemi.pho.repository.FoodRepo;
import dev.nemi.pho.repository.FoodReviewRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {

  private final FoodRepo foodRepo;
  private final FoodReviewRepo foodReviewRepo;
  private final ModelMapper modelMapper;

  @Override
  public PageResponseDTO<FoodViewDTO> getFoods(FoodPageRequestDTO requestDTO) {
    Page<FoodViewDTO> foods = foodRepo.getFoods(
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
    Food food = foodRepo.findById(id).orElseThrow();
    FoodRatingGroupProjection r = foodReviewRepo.getRating(id);
    FoodViewDTO foodViewDTO = modelMapper.map(food, FoodViewDTO.class);
    double avgRate = r != null ? r.getAvgRate() : 0.0;
    long reviewCount = r != null ? r.getReviewCount() : 0L;
    foodViewDTO.setAvgRate(avgRate);
    foodViewDTO.setReviewCount(reviewCount);
    return foodViewDTO;
  }

  @Override
  public Long register(FoodRegisterDTO dto) {
    if (dto.getRegistrar() == null) dto.setRegistrar("hina");
    Food food = modelMapper.map(dto, Food.class);
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
    foodRepo.save(food);
  }

  @Override
  public void delete(long id) {
    foodRepo.deleteById(id);
  }

}
