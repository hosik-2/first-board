package com.hoseok.firstboard.service;

import com.hoseok.firstboard.domain.board.Board;
import com.hoseok.firstboard.domain.board.BoardRepository;
import com.hoseok.firstboard.domain.board.MemoryBoardRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class BoardServiceImplTest {

    private static final Logger log = LoggerFactory.getLogger(BoardServiceImplTest.class);
    BoardRepository boardRepository = new MemoryBoardRepository();

    @BeforeEach
    void beforeAll() {
        boardRepository.clear();
    }

    @AfterEach
    void afterEach() {
        boardRepository.clear();
    }

    @Test
    void save() {

        //given
        Board board1 = new Board("title1", "content1", "writer1");

        //when
        boardRepository.create(board1);

        //then
        List<Board> boardList = boardRepository.findAll();
        assertThat(boardList).contains(board1);

    }

    @Test
    void list() {
        //given
        Board board1 = new Board("title1", "content1", "writer1");
        Board board2 = new Board("title2", "content2", "writer2");
        Board board3 = new Board("title3", "content3", "writer3");

        boardRepository.create(board1);
        boardRepository.create(board2);
        boardRepository.create(board3);

        //when
        List<Board> result = boardRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(3);

    }

    @Test
    void open() {
        //given
        Board board1 = new Board("title1", "content1", "writer1");
        boardRepository.create(board1);

        //when
        Board board = boardRepository.findById(1L);

        //then
        assertThat(board.getId()).isEqualTo(1L);
    }

    @Test
    void delete() {
        //given
        Board board1 = new Board("title1", "content1", "writer1");
        boardRepository.create(board1);

        //when
        log.info("store = {}", boardRepository.findById(1L));
        boardRepository.delete(1L);

        //then
        assertThat(boardRepository.findAll()).isEmpty();
    }
}