package com.hoseok.firstboard.domain.member;

import jakarta.annotation.Nonnull;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberServiceImplTest {

    MemberRepository memberRepository = new MemberMemoryRepository();
    MemberService memberService = new MemberServiceImpl(memberRepository);

    @BeforeEach
    void beforeEach() {
        memberService.clear();
    }

    @Nonnull
    private static Member getMember() {
        Member member1 = new Member("hoseok", "zxvbcd123",
                "123123123", "zxvbcd123@naver.com", "01084335722");
        return member1;
    }

    @Test
    void 회원가입시_ID_중복검사() {
        //given
        Member member1 = getMember();
        Member member2 = new Member("hoseok", "zxvbcd123",
                "123123123", "zxvbcd123@naver.com", "01084335722");

        //when
        memberService.register(member1);
        //then
        assertThrows(IllegalStateException.class, () -> {
            memberService.register(member2);
        });
    }

    @Test
    void 회원가입_성공_로그인_성공() {
        //given
        Member member1 = getMember();

        //when
        memberService.register(member1);
        //then
        assertThat(memberService.login("zxvbcd123", "123123123").orElseThrow()).isEqualTo(member1);
    }

    @Test
    void findLoginId() {
        //given
        Member member1 = getMember();
        memberService.register(member1);
        //when
        Optional<String> id = memberService.findLoginId("hoseok", "zxvbcd123@naver.com", "01084335722");
        String result = id.orElse("fail");
        //then
        assertThat(result).isEqualTo("zxvbcd123");
    }

    @Test
    void findPassword() {//given
        Member member1 = getMember();
        memberService.register(member1);
        //when
        Optional<String> id = memberService.findPassword("zxvbcd123","hoseok", "zxvbcd123@naver.com", "01084335722");
        String result = id.orElse("fail");
        //then
        assertThat(result).isEqualTo("123123123");
    }
}