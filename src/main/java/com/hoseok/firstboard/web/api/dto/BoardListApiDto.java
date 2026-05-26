package com.hoseok.firstboard.web.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BoardListApiDto {

    private Long id;

    private String title;

    private String writer;


}
