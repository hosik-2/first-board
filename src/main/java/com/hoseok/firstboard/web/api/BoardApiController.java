package com.hoseok.firstboard.web.api;

import com.hoseok.firstboard.domain.board.Board;
import com.hoseok.firstboard.domain.board.BoardService;
import com.hoseok.firstboard.domain.member.Member;
import com.hoseok.firstboard.web.api.dto.BoardApiDto;
import com.hoseok.firstboard.web.api.dto.BoardListApiDto;
import com.hoseok.firstboard.web.api.dto.ErrorDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardApiController {

    public final BoardService boardService;

    @GetMapping
    public ResponseEntity<?> boardList(@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
        log.info("api boardList()");
        if (loginMember == null) {
            return new ResponseEntity<>(new ErrorDto("AUTH-EX", "권한 없음. 로그인 필요"), HttpStatus.UNAUTHORIZED);
        }

        List<Board> list = boardService.list();
        if (list == null) {
            throw new NoSuchElementException("게시글이 존재하지 않습니다."); // 게시글이 없을 때 빈 객체가 아닌 예외발생
        }

        List<BoardListApiDto> dtoList = list.stream()
                .map(b -> new BoardListApiDto(b.getId(), b.getTitle(), b.getWriter()))
                .toList();
        //문법이 어렵지만 잘 기억해보자 map은 1:1 매칭, filter는 말 그대로 거르는 것임

        return new ResponseEntity<>(dtoList, HttpStatus.OK); //DTO 변환 완료
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> board(@PathVariable Long id, @SessionAttribute(name = "loginMember", required = false) Member loginMember) {
        log.info("api board()");
        if (loginMember == null) {
            return new ResponseEntity<>(new ErrorDto("AUTH-EX", "권한 없음. 로그인 필요"), HttpStatus.UNAUTHORIZED);
        }

        Board board = boardService.open(id);
        if (board == null) {
            throw new NoSuchElementException("존재하지 않는 게시글입니다.");
        }

        BoardApiDto result = new BoardApiDto(
                board.getId(), board.getTitle(), board.getContent(), board.getWriter()
        );

        return new ResponseEntity<>(result, HttpStatus.OK);
        //TODO 반환 객체 DTO 만들어서 넘기기 -> 완료!
    }

}

