package com.hoseok.firstboard.domain.member;

import java.util.Optional;

public interface MemberService {

    Member register(Member member);

    Optional<Member> login(String loginId, String password);

//    boolean logout(); 컨트롤러에서 직접 처리할 수 있어 서비스 계층에 굳이 필요 없음.

    Optional<String> findLoginId(String name, String email, String phoneNumber);

    Optional<String> findPassword(String loginId, String name, String email, String phoneNumber);

}
