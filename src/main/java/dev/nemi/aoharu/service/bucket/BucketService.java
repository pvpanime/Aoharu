package dev.nemi.aoharu.service.bucket;

import dev.nemi.aoharu.BucketPageRequestDTO;
import dev.nemi.aoharu.PageResponseDTO;

public interface BucketService {
  Long create(BucketCreateDTO dto);

  BucketViewDTO getOne(Long id);

  PageResponseDTO<BucketViewDTO> getListOf(BucketPageRequestDTO dto);

  void update(BucketEditDTO dto);

  void delete(Long id);
}
