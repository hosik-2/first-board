package com.hoseok.firstboard.web.member;

import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MemberLoginForm {

    @Size(min = 5)
    private String loginId;

    @Size(min = 8, max = 20)
    private String password;

}
