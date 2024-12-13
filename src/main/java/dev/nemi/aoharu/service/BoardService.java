package dev.nemi.aoharu.service;

import dev.nemi.aoharu.PageRequestDTO;
import dev.nemi.aoharu.PageResponseDTO;

public interface BoardService {
  Long write(BoardWriteDTO dto);

  BoardViewDTO getOne(Long id);

  PageResponseDTO<BoardViewDTO> search(PageRequestDTO pageRequestDTO);

  void edit(BoardEditDTO dto);

  void delete(Long id);
}
