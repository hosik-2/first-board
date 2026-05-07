package com.hoseok.firstboard.domain.member;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @NoArgsConstructor
public class Member {
    private Long id; // 여러 기능 사용 가능성을 열어두기 위한 래퍼클래스 타입 선언
    private String name;
    private String loginId;
    private String password;
    private String email;
    private String phoneNumber;

    public Member(String name, String loginId, String password, String email, String phoneNumber) { // id는 리포지토리 생성
        this.name = name;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
