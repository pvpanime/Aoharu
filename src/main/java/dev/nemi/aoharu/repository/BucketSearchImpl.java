package dev.nemi.aoharu.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.JPQLQuery;
import dev.nemi.aoharu.prime.Bucket;
import dev.nemi.aoharu.prime.QBucket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class BucketSearchImpl extends QuerydslRepositorySupport implements BucketSearch {

  public BucketSearchImpl() {
    super(Bucket.class);
  }

  @Override
  public Page<Bucket> search(Pageable pageable, String[] searchFor, String search) {
    QBucket bucket = QBucket.bucket;
    JPQLQuery<Bucket> query = from(bucket);

    BooleanBuilder bb = new BooleanBuilder();
    if (searchFor != null && searchFor.length > 0 && search != null && !search.isEmpty()) {
      for (String sFor : searchFor) {
        switch (sFor) {
          case "title":
            bb.or(bucket.title.contains(search));
            break;
          case "description":
            bb.or(bucket.description.contains(search));
            break;
        }
      }

      query.where(bb);
    }



    this.getQuerydsl().applyPagination(pageable, query);


    List<Bucket> buckets = query.fetch();
    long total = query.fetchCount();
    return new PageImpl<>(buckets, pageable, total);
  }
}
