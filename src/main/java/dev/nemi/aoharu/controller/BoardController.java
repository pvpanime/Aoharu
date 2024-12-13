package dev.nemi.aoharu.controller;

import dev.nemi.aoharu.PageRequestDTO;
import dev.nemi.aoharu.PageResponseDTO;
import dev.nemi.aoharu.service.BoardService;
import dev.nemi.aoharu.service.BoardViewDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

@Log4j2
@Controller
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping("/board")
  public String board(
    @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
    Model model
  ) {
    PageResponseDTO<BoardViewDTO> dto = boardService.search(pageRequestDTO);
    model.addAttribute("dto", dto);
//    model.addAttribute("currentPage", pageRequestDTO.getPage());
//    model.addAttribute("maxPage", pageRequestDTO.getPage());
    return "board/index";
  }


  @GetMapping("/board/view/{id}")
  public String view(
    @PathVariable long id,
    @ModelAttribute("requestDTO") PageRequestDTO pageRequestDTO,
    Model model
    ) {
    BoardViewDTO board = boardService.getOne(id);
    model.addAttribute("board", board);
    return "board/view";
  }

}
