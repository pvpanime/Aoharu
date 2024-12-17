package dev.nemi.aoharu;

import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.prime.BoardComment;
import dev.nemi.aoharu.repository.BoardCommentRepo;
import dev.nemi.aoharu.repository.BoardRepo;
import dev.nemi.aoharu.service.board.BoardListViewDTO;
import dev.nemi.aoharu.service.board.BoardService;
import dev.nemi.aoharu.service.board.BoardViewDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@SpringBootTest
public class BoardTest {

  @Autowired
  private BoardService boardService;

  @Autowired
  private BoardRepo boardRepo;

  @Autowired
  private BoardCommentRepo boardCommentRepo;


  @Test
  public void boardListTest() {
    PageResponseDTO<BoardViewDTO> responseDTO = boardService.search(BoardPageRequestDTO.DEFAULT);
    log.info("BoardList={}", responseDTO);
  }

  @Test
  public void addBoardTest() {
//    IntStream.rangeClosed(1, 100).forEach(i -> {
//      Board board = Board.builder().title("Title="+i).content("This is the " + (i * i) + "-th content.").userid("hina").status(1).build();
//      Board result = boardRepo.save(board);
//
//      log.info("Board={}", result.getId());
//    });
  }


  @Test
  public void addBoardCommentFromRepoTest() {
    Board b = boardRepo.findById(2L).orElseThrow();
    BoardComment cResult = boardCommentRepo.save(BoardComment.builder().board(b).userid("hina").content("test").build());
    log.info(cResult);
  }

  @Test
  public void getCommentsOfBoardTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("id").descending());
    Page<BoardComment> comments = boardCommentRepo.getCommentsOfBoard(2L, pgb);
    comments.getContent().forEach(log::info);
  }

  @Test
  @Transactional // <-- Without this, this test fails.
  public void getCommentsWithBoardTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("id").descending());
    Page<BoardComment> comments = boardCommentRepo.getCommentsOfBoard(2L, pgb);
    comments.getContent().forEach(log::info);
    Board b = comments.getContent().get(0).getBoard();
    log.info("Board={}", b);
  }

  @Test
  public void getBoardListTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("id").ascending());
    Page<BoardListViewDTO> boardListView = boardRepo.searchAsDTO(pgb, new String[]{"user"}, "hina");

    boardListView.getContent().forEach(log::info);

  }


}
