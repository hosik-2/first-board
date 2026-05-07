package com.hoseok.firstboard.domain.board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@RequiredArgsConstructor 생성자 선언이 없으면 이걸로 대체 가능 보통 이걸 많이 씀
public class BoardServiceImpl implements BoardService { // 현재는 리포지토리 호출만 있지만 나중에 비즈니스 로직 추가 등
    // 서비스 레벨이 필요함 그 기능등도 impl에 선언해야 함.

    private final BoardRepository boardRepository;

    @Autowired // 직접 생성자 선언 정의 / 질문: 근데 이거 왜 하는 거였지? 싱글톤 객체로 만들고 스프링이 쓰려고 만드는 거였나?
                                        // 맞음 직접 new 하고 객체 만들면 나중에 리포지토리 교체시 또 바꿔야 함
                                        // 그래서 추상화를 통해 인터페이스 선언 후 @Primary 등 다른 구현체를 만들고 선택 가능
    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public void create(Board board) { // 생성 -> 리포지토리에 글을 같이 넘겨줌 || id는 리포지토리에서 관리 -> 이거 문제 있나?
                                        // 괜찮음 원래 번호를 매겨서 저장하는 건 저장소 역할임
                                        // 나중에 DB를 사용하게 되면 DB가 하거나 JPA가 담당하게 될 거임. 지금은 학습용
        boardRepository.create(board);
    }

    @Override
    public void update(Long id, Board board) { // 수정 -> 수정된 게시물과 수정해야 할 게시물의 id를 같이 넘겨줌
        boardRepository.edit(id, board);
    }

    @Override
    public List<Board> list() { // 글 목록 조회 -> 리포지토리에 전체조회 메서드 사용 후 Board형 리스트 반환
        return boardRepository.findAll();
    }

    @Override
    public Board open(Long id) { // 글 상세 조회 -> 조회할 글 id 받은 후 id로 글 조회
        return boardRepository.findById(id);
    }

    @Override
    public void delete(Long id) { // 글 삭제 -> 삭제할 글 id 받은 후 삭제 메서드 호출
        boardRepository.delete(id);
    }
}
