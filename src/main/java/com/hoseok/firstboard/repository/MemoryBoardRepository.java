package com.hoseok.firstboard.repository;

import com.hoseok.firstboard.Board;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Primary // 자동 DI 시 첫 번째 사용될 리포지토리
public class MemoryBoardRepository implements BoardRepository{

    private final Map<Long, Board> store = new ConcurrentHashMap<>(); // 글 저장할 저장소 <id, 게시글> + ConcurrentHashMap(동시 접근 가능)
    private Long id = 0L;

    @Override
    public void create(Board board) { // 생성기능 -> id를 먼저 올려서 게시판에 부여 후 저장소에 저장
        board.setId(++id);
        store.put(board.getId(), board);
    }

    @Override
    public void edit(Long id, Board board) { // 수정 기능 -> 게시물을 id로 찾고 찾은 게시물을 파라미터 값으로 수정
        Board findBoard = store.get(id);

        if (findBoard == null) {
            return; // 찾은 게시물이 없을 시 리턴
        }
        findBoard.setTitle(board.getTitle());
        findBoard.setContent(board.getContent());
        findBoard.setWriter(board.getWriter());
    }

    @Override
    public void delete(Long id) { // 삭제 기능
        store.remove(id);
    }

    @Override
    public List<Board> findAll() { // 전체 조회 기능 -> 글목록 시 쓰임
        return new ArrayList<>(store.values()); // 이게 새로운 리스트로 반환하는게 뭐가 좋다 그랬는데 찾아보자
        // 새로운 리스트를 만들어서 반환하면 store.values()를 쓸 때 실제 저장소에 영향을 주지 않을 수 있음 (캡슐화!)
    }

    @Override
    public Board findById(Long id) { // 게시물 상세 조회시 사용
        return store.get(id);
    }

    @Override
    public void clear() { // 왜 만들었지 -> 테스트 코드 사용시 쓰려고
        store.clear();
    }
}
