package dev.nemi.aoharu;

import dev.nemi.aoharu.repository.BoardRepo;
import dev.nemi.aoharu.repository.BucketRepo;
import dev.nemi.aoharu.prime.Board;
import dev.nemi.aoharu.prime.Bucket;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@SpringBootTest
@Log4j2
public class BoardRepoTest {

  @Autowired
  private BoardRepo boardRepo;

  @Autowired
  private BucketRepo bucketRepo;

//  @Test
//  public void insertTest() {
//    IntStream.rangeClosed(1, 100).forEach(i -> {
//      Board board = Board.builder().title("Title="+i).content("This is the " + (i * i) + "-th content.").userid("hina").status(1).build();
//      Board result = boardRepo.save(board);
//
//      log.info("Board={}", result.getId());
//    });
//  }

//  @Test
//  public void selectTest() {
//    Optional<Board> board = boardRepo.findById(250L);
//    Assertions.assertTrue(board.isPresent());
//    log.info("Board={}", board.get());
//  }
//
//  @Test
//  public void updateTest() {
//    Optional<Board> found = boardRepo.findById(250L);
//    Assertions.assertTrue(found.isPresent());
//    Board board = found.get();
//    board.update("Hina chaan", "kawaii");
//    boardRepo.save(board);
//  }

  @Test
  public void paginationTest() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("updated").descending());
    Page<Board> boardPage = boardRepo.findAll(pageable);
    logPage(boardPage);
  }


//  @Test
//  public void insertBucketTest() {
//    IntStream.rangeClosed(1, 50).forEach(i -> {
//      Random random = new Random();
//      long anySecond = random.nextLong(1800L, 200000L);
//      LocalDateTime ldt = LocalDateTime.now().plusSeconds(anySecond);
//      Bucket bucket = Bucket.builder().title("BucketList").description("This should be done by " + ldt).dueTo(ldt).userid("hina").status(0).build();
//      bucketRepo.save(bucket);
//    });
//  }
//
//  @Test
//  public void selectBucketTest() {
//    Optional<Bucket> found = bucketRepo.findById(1L);
//    Bucket bucket = found.orElseThrow();
//    log.info("Bucket={}", bucket);
//  }
//
//  @Test
//  public void updateBucketTest() {
//    Optional<Bucket> found = bucketRepo.findById(1L);
//    Bucket bucket = found.orElseThrow();
//    bucket.update("Trip", "Go trip!", LocalDateTime.now(), bucket.getStatus());
//    bucketRepo.save(bucket);
//    log.info("Bucket={}", bucket);
//  }


  private <T> void logPage(Page<T> pg) {
    log.info("total count = {}", pg.getTotalElements());
    log.info("total pages = {}", pg.getTotalPages());
    log.info("page number = {}", pg.getNumber());
    log.info("page size = {}", pg.getSize());
    pg.forEach(log::info);
  }

  @Test
  public void bucketPageTest() {
    Pageable pageable = PageRequest.of(0, 10, Sort.by("dueTo").ascending());
    Page<Bucket> bucketPage = bucketRepo.findAll(pageable);

    logPage(bucketPage);
  }

  @Test
  public void repoJpaTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
    Page<Bucket> page = bucketRepo.findByTitleContainingOrderByAddedDesc("Tri", pgb);

    logPage(page);
  }

  @Test
  public void repoAnnoTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
    Page<Bucket> page = bucketRepo.endsWith("1", pgb);
    logPage(page);
  }

  @Test
  public void querydslTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
    Page<Board> result = boardRepo.realSearch(
      pgb, new String[] {"title", "content"},
      "4"
    );

    logPage(result);
  }

  @Test
  public void bucketQuerydslTest() {
    Pageable pgb = PageRequest.of(0, 10, Sort.by("added").descending());
    Page<Bucket> result = bucketRepo.search(
      pgb, new String[] { "title", "description" }, "12"
    );

    logPage(result);
  }


}
