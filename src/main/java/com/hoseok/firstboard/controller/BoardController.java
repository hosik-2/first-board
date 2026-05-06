package com.hoseok.firstboard.controller;

import com.hoseok.firstboard.Board;
import com.hoseok.firstboard.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public String boardList(Model model) {
        List<Board> list = boardService.list();
        model.addAttribute("list", list);
        log.info("boardList()");
        log.info(String.valueOf(list.size()));
        log.info(list.toString());
        return "board-list";
    }

    @GetMapping("/{id}")
    public String board(@PathVariable Long id, Model model) {
        Board board = boardService.open(id);
        model.addAttribute("board", board);
        log.info("board()");
        return "board";
    }

    @GetMapping("/edit")
    public String addPage(Model model) {
        model.addAttribute("board", new Board());
        log.info("addPage()");
        return "board-edit";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Long id, Model model) {
        Board board = boardService.open(id);
        model.addAttribute("board", board);
        log.info("editPage()");
        return "board-edit";
    }

    @PostMapping
    public String add(@ModelAttribute Board board) {
        log.info("add()");
        boardService.create(board);
        return "redirect:/board";
    }

    @PostMapping("/{id}")
    public String edit(@PathVariable Long id, @ModelAttribute Board board) {
        boardService.update(id, board);
        return "redirect:/board";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        boardService.delete(id);
        log.info("delete()");
        return "redirect:/board";
    }

}
