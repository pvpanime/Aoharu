package dev.nemi.aoharu.service.bucket;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketCreateDTO {
  private String title;
  private String description;
  private String userid;
  private LocalDateTime dueTo;
}
