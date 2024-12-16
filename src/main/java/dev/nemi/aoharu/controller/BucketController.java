package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.BoardPageRequestDTO;
import dev.nemi.aoharu.BucketPageRequestDTO;
import dev.nemi.aoharu.PageResponseDTO;
import dev.nemi.aoharu.service.bucket.BucketService;
import dev.nemi.aoharu.service.bucket.BucketViewDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BucketController {

  private final BucketService bucketService;

  @GetMapping("/bucket")
  public String bucket(
    @Valid @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
    BindingResult br,
    Model model
  ) {
    if (br.hasErrors()) { return "redirect:/bucket"; }

    PageResponseDTO<BucketViewDTO> dto = bucketService.getListOf(pageRequestDTO);
    model.addAttribute("dto", dto);

    return "bucket/index";
  }

  @GetMapping("/bucket/view/{id}")
  public String view(
    @PathVariable long id,
    @Valid @ModelAttribute("requestDTO") BucketPageRequestDTO pageRequestDTO,
    BindingResult br,
    Model model
  ) {
    if (br.hasErrors()) { return "redirect:/bucket/view/"+id; }
    BucketViewDTO bucket = bucketService.getOne(id);
    model.addAttribute("bucket", bucket);
    model.addAttribute("deleteAction", "/bucket/delete/"+id);
    return "bucket/view";
  }

  @GetMapping("/bucket/create")
  public String create(
    Model model
  ) {
    model.addAttribute("useEdit", false);
    model.addAttribute("useTitle", "Write");
    model.addAttribute("useAction", "/bucket/create");
    return "bucket/edit";
  }

  @GetMapping("/bucket/edit/{id}")
  public String edit(
    @Valid @ModelAttribute("requestDTO") BoardPageRequestDTO pageRequestDTO,
    BindingResult pageBR,
    @PathVariable long id,
    Model model
  ) {
    if (pageBR.hasErrors()) return "redirect:/board/edit/"+id;

    BucketViewDTO bucket = bucketService.getOne(id);
    model.addAttribute("useEdit", true);
    model.addAttribute("useTitle", "Edit");
    model.addAttribute("useAction", "/bucket/edit");
    model.addAttribute("bucket", bucket);
    return "bucket/edit";
  }
}
