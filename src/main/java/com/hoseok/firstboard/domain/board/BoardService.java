package com.hoseok.firstboard.domain.board;

import java.util.List;

public interface BoardService {

    public void create(Board board); // 생성

    public List<Board> list(); // 글목록 조회

    public Board open(Long id); // 글 상세 조회

    public void delete(Long id); // 글 삭제

    public void update(Long id, Board board); // 글 수정

}
