package com.hoseok.firstboard.web.board;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoardSaveForm {

    private Long id;

    @NotBlank
    private String title;

    @Size(min = 10, max = 5000)
    private String content;

    private String writer; // 따로 세션에서 객체 받아서 처리할 거라 글생성 폼에 검증 필요 X
}
