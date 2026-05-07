package com.hoseok.firstboard.web.board;


import com.hoseok.firstboard.domain.board.Board;
import com.hoseok.firstboard.domain.board.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping
    public String boardList(Model model) {
        List<Board> list = boardService.list();
        model.addAttribute("list", list);
        log.info("boardList()");
        log.info(String.valueOf(list.size()));
        log.info(list.toString());
        return "/board/board-list";
    }

    @GetMapping("/{id}")
    public String board(@PathVariable Long id, Model model) {
        log.info("board()");
        Board board = boardService.open(id);
        model.addAttribute("board", board);
        log.info(board.toString());
        return "/board/board-view";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        log.info("addForm()");
        model.addAttribute("board", new BoardSaveForm()); // board-form이 board객체를 무조건 받아야 하기 때문에
        // 빈 객체를 전달 (수정과 같은 폼을 공유해서 씀)
        return "/board/board-form";
    }

    @PostMapping("/add")
    public String add(@Validated @ModelAttribute("board") BoardSaveForm form, BindingResult bindingResult) {
                                                    // BindingResult는 검증해야 할 객체 바로 뒤에 선언해야 함
                                                    // DTO 사용으로 선언 타입과 객체 이름이 바뀌었으므로, @ModelAttribute("board") 처럼 모델 이름 명시
        log.info("add()");

        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생! error={}", bindingResult);
            return "/board/board-form"; // 에러 발생 시 입력 폼으로 이동
        }

        Board board = new Board(); // 서비스 계층 전달 할 도메인 타입 객체 선언
        board.setTitle(form.getTitle());
        board.setContent(form.getContent());
        board.setWriter(form.getWriter()); // DTO에서 도메인 객체로 데이터 맵핑(?)
        boardService.create(board);
        log.info("createBoard.id={}", board.getId());
        return "redirect:/board";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        log.info("editForm()");
        Board findBoard = boardService.open(id);
        model.addAttribute("board", findBoard);
        log.info(findBoard.toString());
        return "/board/board-form";
    }

    @PostMapping("/{id}/edit")
    public String edit(@PathVariable Long id, @Validated @ModelAttribute("board") BoardUpdateForm form, BindingResult bindingResult) {
                                                        // BindingResult는 검증해야 할 객체 바로 뒤에 선언해야 함
                                                        // DTO 사용으로 선언 타입과 객체 이름이 바뀌었으므로, @ModelAttribute("board") 처럼 모델 이름 명시
        if (bindingResult.hasErrors()) {
            log.info("검증 오류 발생! error={}", bindingResult);
            return "/board/board-form"; // 에러 발생 시 다시 입력 폼으로 이동
        }

        log.info("edit()");
        Board board = new Board(); // 서비스 계층 전달 할 도메인 타입 객체 선언
        board.setWriter(form.getWriter());
        board.setTitle(form.getTitle());
        board.setContent(form.getContent()); // DTO에서 도메인 객체로 데이터 맵핑(?)
        boardService.update(id, board);
        return "redirect:/board";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        log.info("delete()");
        boardService.delete(id);
        log.info(String.valueOf(boardService.open(id))); // 잘 삭제 됐는지 확인 차 로깅
        return "redirect:/board";
    }
}