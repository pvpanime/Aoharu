package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardSearch {
  Page<Board> notSearch(Pageable pageable);
  Page<Board> realSearch(Pageable pageable, String[] searchFor, String search);
}
