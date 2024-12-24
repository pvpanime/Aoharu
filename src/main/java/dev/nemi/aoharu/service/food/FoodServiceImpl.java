package dev.nemi.aoharu.service.food;

import dev.nemi.aoharu.PageResponseDTO;
import dev.nemi.aoharu.prime.Food;
import dev.nemi.aoharu.repository.FoodRepo;
import dev.nemi.aoharu.repository.FoodReviewRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

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
  public PageResponseDTO<FoodViewImageSupportDTO> getFoodsWithImage(FoodPageRequestDTO requestDTO) {
    Page<FoodViewImageSupportDTO> foods = foodRepo.getFoodsImageSupport(
      requestDTO.getPageable("id"),
      requestDTO.getSearchName(),
      requestDTO.getMinPrice(),
      requestDTO.getMaxPrice(),
      requestDTO.getMinRate(),
      requestDTO.getUntil()
    );

    return PageResponseDTO.<FoodViewImageSupportDTO>withAll()
      .pageRequestDTO(requestDTO)
      .dtoList(foods.getContent())
      .total(foods.getTotalElements())
      .build();
  }

  @Override
  public FoodViewDTO getOne(long id) {
    Food food = foodRepo.findById(id).orElseThrow();

    FoodRatingGroupProjection r = foodReviewRepo.getRating(id);
    Double avgRate = r != null ? r.getAvgRate() : 0.0;
    Long reviewCount = r != null ? r.getReviewCount() : 0L;

    FoodViewDTO foodViewDTO = modelMapper.map(food, FoodViewDTO.class);

    foodViewDTO.setAvgRate(avgRate);
    foodViewDTO.setReviewCount(reviewCount);
    return foodViewDTO;
  }

  @Override
  public FoodViewImageSupportDTO getOneWithImage(long id) {
    Food food = foodRepo.getOneWithImages(id).orElseThrow();

    FoodRatingGroupProjection r = foodReviewRepo.getRating(id);
    Double avgRate = r != null ? r.getAvgRate() : 0.0;
    Long reviewCount = r != null ? r.getReviewCount() : 0L;

    List<FoodImageDTO> imageList = food.getImages().stream().sorted().map(
      im -> FoodImageDTO.builder()
        .id(im.getId()).name(im.getFilename()).ordinal(im.getOrdinal()).build()
    ).toList();

    FoodViewImageSupportDTO foodViewDTO = FoodViewImageSupportDTO.builder()
      .id(food.getId())
      .name(food.getName())
      .description(food.getDescription())
      .price(food.getPrice())
      .stock(food.getStock())
      .opened(food.getOpened())
      .close(food.getClose())
      .registrar(food.getRegistrar())
      .added(food.getAdded())
      .updated(food.getUpdated())
      .reviewCount(reviewCount)
      .avgRate(avgRate)
      .images(imageList)
      .build();

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
