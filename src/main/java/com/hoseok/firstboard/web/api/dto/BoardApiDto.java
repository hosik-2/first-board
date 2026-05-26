package com.hoseok.firstboard.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardApiDto {

    private Long id;

    private String title;

    private String content;

    private String writer;
}
