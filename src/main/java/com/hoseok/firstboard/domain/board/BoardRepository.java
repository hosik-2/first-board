package com.hoseok.firstboard.domain.board;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardRepository {

    public void create(Board board);

    public void delete(Long id);

    public List<Board> findAll();

    public Board findById(Long id);

    public void clear();

    void edit(Long id, Board board); // 수정기능
}
