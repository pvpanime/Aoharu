package dev.nemi.aoharu.service.board;

import dev.nemi.aoharu.PageRequestDTO;
import dev.nemi.aoharu.PageResponseDTO;

public interface BoardCommentService {
  Long add(BoardCommentWriteDTO dto);

  void modify(Long cid, BoardCommentEditDTO dto);

  PageResponseDTO<BoardCommentViewDTO> getCommentsOf(Long bid, PageRequestDTO requestDTO);
}
