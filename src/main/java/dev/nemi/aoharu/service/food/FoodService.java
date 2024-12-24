package dev.nemi.aoharu.service.food;

import dev.nemi.aoharu.PageResponseDTO;

public interface FoodService {
  PageResponseDTO<FoodViewDTO> getFoods(FoodPageRequestDTO requestDTO);

  FoodViewDTO getOne(long id);

  Long register(FoodRegisterDTO dto);

  void edit(FoodEditDTO dto);

  void delete(long id);
}
