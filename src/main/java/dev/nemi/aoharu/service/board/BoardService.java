package dev.nemi.aoharu.service.board;

import dev.nemi.aoharu.BoardPageRequestDTO;
import dev.nemi.aoharu.PageResponseDTO;

public interface BoardService {
  Long write(BoardWriteDTO dto);

  BoardViewDTO getOne(Long id);

  PageResponseDTO<BoardListViewDTO> getList(BoardPageRequestDTO pageRequestDTO);

  void edit(BoardEditDTO dto);

  void delete(Long id);
}
