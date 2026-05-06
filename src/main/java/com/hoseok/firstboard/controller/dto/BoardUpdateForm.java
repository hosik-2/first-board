package com.hoseok.firstboard.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BoardUpdateForm {

    private Long id;

    @NotBlank
    private String title;

    @Size(min = 10, max = 5000)
    private String content;

    @NotBlank
    private String writer;
}
