package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepo extends JpaRepository<Board, Long>, BoardSearch {
}
