package com.hoseok.firstboard.web.member;

import com.hoseok.firstboard.domain.member.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberSaveForm {

    @NotBlank
    private String name;

    @Size(min = 5)
    private String loginId;

    @Size(min = 8, max = 20)
    private String password;

    @Email
    private String email;

    @Size(min = 10)
    private String phoneNumber;

    public Member toEntity() {
        Member member = new Member();
        member.setName(name);
        member.setLoginId(loginId);
        member.setPassword(password);
        member.setEmail(email);
        member.setPhoneNumber(phoneNumber);
        return member;
    }
}
