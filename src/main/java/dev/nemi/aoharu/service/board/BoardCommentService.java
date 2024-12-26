package dev.nemi.aoharu.service.board;

import dev.nemi.aoharu.dto.PageRequestDTO;
import dev.nemi.aoharu.dto.PageResponseDTO;

public interface BoardCommentService {
  Long add(BoardCommentWriteDTO dto);

  void modify(Long cid, BoardCommentEditDTO dto);

  PageResponseDTO<BoardCommentViewDTO> getCommentsOf(Long bid, PageRequestDTO requestDTO);

  void delete(Long cid);
}
