package dev.nemi.aoharu.service.board;


import dev.nemi.aoharu.BoardPageRequestDTO;
import dev.nemi.aoharu.PageResponseDTO;
import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.repository.BoardRepo;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class BoardServiceImpl implements BoardService {

  private final ModelMapper modelMapper;
  private final BoardRepo boardRepo;

  @Override
  public Long write(BoardWriteDTO dto) {
    Board board = modelMapper.map(dto, Board.class);
    Long id = boardRepo.save(board).getId();
    return id;
  }

  @Override
  public BoardViewDTO getOne(Long id) {
    Optional<Board> result = boardRepo.findById(id);
    Board board = result.orElseThrow();
    BoardViewDTO dto = modelMapper.map(board, BoardViewDTO.class);
    return dto;
  }

  @Override
  public PageResponseDTO<BoardViewDTO> search(BoardPageRequestDTO pageRequestDTO) {
    Page<Board> page = boardRepo.realSearch(pageRequestDTO.getPageable("id"), pageRequestDTO.getSearchFor(), pageRequestDTO.getSearch());
    List<BoardViewDTO> list = page.getContent().stream().map(board -> modelMapper.map(board, BoardViewDTO.class)).toList();

    return PageResponseDTO.<BoardViewDTO>withAll()
      .pageRequestDTO(pageRequestDTO)
      .dtoList(list)
      .total(page.getTotalElements())
      .build();
  }

  @Override
  public void edit(BoardEditDTO dto) {
    Optional<Board> result = boardRepo.findById(dto.getId());
    Board board = result.orElseThrow();
    board.editPayload(dto.getTitle(), dto.getContent());
    boardRepo.save(board);
  }

  @Override
  public void delete(Long id) {
    boardRepo.deleteById(id);
  }
}
