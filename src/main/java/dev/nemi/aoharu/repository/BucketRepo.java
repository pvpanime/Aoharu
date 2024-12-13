package dev.nemi.aoharu.repository;

import dev.nemi.aoharu.prime.Bucket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BucketRepo extends JpaRepository<Bucket, Long>, BucketSearch {
  Page<Bucket> findByTitleContainingOrderByAddedDesc(String title, Pageable pageable);

  @Query("select b from Bucket b where b.description like concat('%', :kw)")
  Page<Bucket> endsWith(String kw, Pageable pageable);
}
