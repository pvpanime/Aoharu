package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.BoardComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BoardCommentRepo extends JpaRepository<BoardComment, Long> {

  @Query("select c from BoardComment c where c.board.bid = :bid")
  Page<BoardComment> getCommentsOfBoard(long bid, Pageable pageable);

}
