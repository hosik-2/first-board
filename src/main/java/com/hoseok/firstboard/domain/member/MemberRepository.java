package com.hoseok.firstboard.domain.member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    Member save(Member member); // 회원 저장 후 멤버 객체 반환

    Optional<Member> findById(Long id); // 내부 로직 구현 시 사용

    Optional<Member> findByLoginId(String loginId); // 로그인 시 사용

    Optional<Member> findByNameAndEmailAndPhoneNumber(
            String name, String email, String phoneNumber); // 아이디 찾기용

    Optional<Member> findByLoginIdAndNameAndEmailAndPhoneNumber(
            String loginId, String name, String email, String phoneNumber); // PW 찾기용

    List<Member> findAll(); //회원 목록 조회

    void clearStore(); //저장소 비우기(테스트용)

    void delete(Long id);
}
