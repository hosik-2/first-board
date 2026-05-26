package com.hoseok.firstboard.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDto {

    private String ex;
    private String message;

}
