package com.hoseok.firstboard.web.member;

import com.hoseok.firstboard.domain.member.Member;
import com.hoseok.firstboard.domain.member.MemberService;
import com.hoseok.firstboard.domain.member.MemberServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    MemberService memberService;

    @BeforeEach
    void beforeEach() throws Exception {
        memberService.clear();
        // 2. 가짜 요청 대신 진짜 객체를 가입시킨다.
        Member member = new Member("name", "ididid", "12345678", "email@email.com", "123123123");
        memberService.register(member);
    }

    @Test
    void 로그인_폼() throws Exception {
        mockMvc.perform(get("/member/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("member/loginForm"))
                .andExpect(model().attributeExists("loginForm"))
                .andDo(print());
    }

    @Test
    void 로그인_검증() throws Exception {
        mockMvc.perform(post("/member/login")
                        .param("loginId", "ididid")
                        .param("password", "12345678"))
                .andExpect(status().is3xxRedirection())
                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER, notNullValue()))
                .andExpect(redirectedUrl("/"))
                .andDo(print());
    }

    @Test
    void 로그인_실패_비밀번호_길이() throws Exception {

        mockMvc.perform(post("/member/login") //비밀번호 Size 검증 테스트
                        .param("loginId", "wrong")
                        .param("password", "wrong2"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER, nullValue()))
                .andExpect(view().name("member/loginForm"))
                .andExpect(model().hasErrors())
                .andDo(print());

    }

    @Test
    void 로그인_실패_아이디_불일치() throws Exception{
        mockMvc.perform(post("/member/login") // 존재하지 않는 아이디 로그인 검증
                        .param("loginId", "wrong")
                        .param("password", "wrong2222"))
                .andExpect(status().isOk())
                .andExpect(request().sessionAttribute(SessionConst.LOGIN_MEMBER, nullValue()))
                .andExpect(view().name("member/loginForm"))
                .andExpect(model().hasErrors())
                .andDo(print());
    }

    @Test
    void 회원가입_성공() throws Exception{
        mockMvc.perform(post("/member/register")
                        .param("name", "name")
                        .param("loginId", "loginId")
                        .param("password", "correctpw")
                        .param("email", "correct@email.com")
                        .param("phoneNumber", "00000000000"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"))
                .andDo(print());

        assertThat(memberService.findLoginId("name", "correct@email.com", "00000000000")).isNotEmpty();
        assertThat(memberService.findLoginId("name", "correct@email.com", "00000000000"))
                .isPresent()
                .hasValue("loginId"); // Optional을 처리하는 방법

    }

    @Test
    void 회원가입_실패_중복() throws Exception{
        mockMvc.perform(post("/member/register")
                        .param("name", "name")
                        .param("loginId", "ididid")
                        .param("password", "correctpw")
                        .param("email", "correct@email.com")
                        .param("phoneNumber", "000000000000"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(request().sessionAttribute("registerForm", nullValue()))
                .andExpect(view().name("member/registerForm"))
                .andDo(print());
    }

    @Test
    void 회원가입_실패_길이_검증() throws Exception{
        mockMvc.perform(post("/member/register")
                        .param("name", "name")
                        .param("loginId", "fail")
                        .param("password", "wrong")
                        .param("email", "correct@email.com")
                        .param("phoneNumber", "333"))
                .andExpect(status().isOk())
                .andExpect(model().hasErrors())
                .andExpect(request().sessionAttribute("registerForm", nullValue()))
                .andExpect(view().name("member/registerForm"))
                .andDo(print());
    }
}