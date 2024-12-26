package dev.nemi.aoharu.service.board;

import dev.nemi.aoharu.dto.PageResponseDTO;

public interface BoardService {
  Long write(BoardWriteDTO dto);

  BoardViewDTO getOne(Long id);

  PageResponseDTO<BoardListViewDTO> getList(BoardPageRequestDTO pageRequestDTO);

  void edit(BoardEditDTO dto);

  void delete(Long id);
}
