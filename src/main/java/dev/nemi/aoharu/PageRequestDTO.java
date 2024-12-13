package dev.nemi.aoharu;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

  private static final int DEFAULT_PAGE_SIZE = 10;
  public static final String DEFAULT_SIZE_STR = DEFAULT_PAGE_SIZE + "";
  public static final PageRequestDTO DEFAULT = PageRequestDTO.builder().build();

  @Builder.Default
  private int page = 1;
  
  @Builder.Default
  private int size = DEFAULT_PAGE_SIZE;
  
  private String[] searchFor;
  
  private String search;
  
  public boolean isSearchingFor(String s) {
      if (searchFor == null || s == null) {
          return false;
      }
      for (String searchItem : searchFor) {
          if (s.equals(searchItem)) {
              return true;
          }
      }
      return false;
  }

  public Pageable getPageable(String ...props) {
    return PageRequest.of(this.page - 1, this.size, Sort.by(props).descending());
  }




  public String usePage(int page) {
      StringBuilder sb = new StringBuilder();
      if (page > 1) {
        sb.append("page=").append(page);
      }
      if (size != DEFAULT_PAGE_SIZE) {
        sb.append("&size=").append(size);
      }
      if (search != null && !search.isEmpty()) {
        sb.append("&search=").append(URLEncoder.encode(search, StandardCharsets.UTF_8));
      }
      if (searchFor != null) {
        for (String s : searchFor) {
          if (s != null && !s.isEmpty()) sb.append("&searchFor=").append(s);
        }
      }
      return sb.toString();
  }

  public String usePage() {
    return usePage(page);
  }

  public String useQuery(int page) {
    String s = this.usePage(page);
    if (s.isEmpty()) return "";
    return "?" + s;
  }

  public String useQuery() {
    return useQuery(page);
  }
}
