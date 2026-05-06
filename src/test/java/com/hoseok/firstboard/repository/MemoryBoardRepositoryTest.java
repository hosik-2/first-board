package com.hoseok.firstboard.repository;

import com.hoseok.firstboard.Board;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MemoryBoardRepositoryTest {

    @Test
    void findAll() {

        //given
        MemoryBoardRepository memoryBoardRepository = new MemoryBoardRepository();

        Board board1 = new Board("1", "12", "12");
        Board board2 = new Board("2", "12", "12");
        Board board3 = new Board("3", "12", "12");

        memoryBoardRepository.create(board1);
        memoryBoardRepository.create(board2);
        memoryBoardRepository.create(board3);

        //when
        List<Board> boardList = memoryBoardRepository.findAll();

        //then
        Assertions.assertThat(boardList).contains(board1);
        Assertions.assertThat(boardList).contains(board2);
        Assertions.assertThat(boardList).contains(board3);

    }
}