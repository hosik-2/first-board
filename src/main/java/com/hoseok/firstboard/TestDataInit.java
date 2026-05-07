package com.hoseok.firstboard;

import com.hoseok.firstboard.domain.member.Member;
import com.hoseok.firstboard.domain.member.MemberService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestDataInit {

    @Autowired
    private final MemberService memberService;

    @PostConstruct
    public void init() { //테스트 데이터 추가
        memberService.register(new Member("hoseok", "zxvbcd123",
                "123123123", "zxvbcd123@naver.com", "01084335722"));

    }

}
