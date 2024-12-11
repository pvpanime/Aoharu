package dev.nemi.aoharu.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.prime.QBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BoardSearchImpl extends QuerydslRepositorySupport implements BoardSearch {

  public BoardSearchImpl() {
    super(Board.class);
  }


  @Override
  public Page<Board> notSearch(Pageable pageable) {
    return null;
  }

  @Override
  public Page<Board> realSearch(Pageable pageable, String[] searchFor, String search) {
    QBoard board = QBoard.board;
    JPQLQuery<Board> query = this.from(board);

    if (searchFor != null && searchFor.length > 0 && search != null && !search.isEmpty()) {

      BooleanBuilder bb = new BooleanBuilder();

      for (String sFor : searchFor) {
        switch (sFor) {
          case "title":
            bb.or(board.title.contains(search));
            break;
          case "content":
            bb.or(board.content.contains(search));
            break;
          case "user":
            bb.or(board.userid.contains(search));
            break;
        }
      }

      query.where(bb);
    }

    this.getQuerydsl().applyPagination(pageable, query);
    List<Board> boards = query.fetch();

    long count = query.fetchCount();

    return new PageImpl<>(boards, pageable, count);
  }
}
