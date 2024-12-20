package dev.nemi.aoharu.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UploadResultDTO {

  private String uuid;

  private String filename;

  private String mime;

  public String getLink() {
    if (mime != null && mime.startsWith("image/")) {
      return "/th/"+uuid+"_"+filename;
    } else {
      return "/i/"+uuid+"_"+filename;
    }
  }
}
