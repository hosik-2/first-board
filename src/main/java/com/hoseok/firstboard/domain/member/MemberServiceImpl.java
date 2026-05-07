package com.hoseok.firstboard.domain.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;

    @Override
    public Member register(Member member) {
        memberRepository.findByLoginId(member.getLoginId())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 가입된 ID입니다.");});
        return memberRepository.save(member);
    }

    @Override
    public Optional<Member> login(String loginId, String password) {
        return memberRepository.findByLoginId(loginId)
                .filter(m -> m.getPassword().equals(password));
    }

    @Override
    public Optional<String> findLoginId(String name, String email, String phoneNumber) {
        return memberRepository.findByNameAndEmailAndPhoneNumber(name, email, phoneNumber).map(Member::getLoginId);
    }

    @Override
    public Optional<String> findPassword(String loginId, String name, String email, String phoneNumber) {
        return memberRepository.findByLoginIdAndNameAndEmailAndPhoneNumber(loginId, name, email, phoneNumber).map(Member::getPassword);
    }
}
