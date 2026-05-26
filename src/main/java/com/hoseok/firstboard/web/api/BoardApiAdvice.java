package com.hoseok.firstboard.web.api;

import com.hoseok.firstboard.web.api.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class BoardApiAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ErrorDto notFound(NoSuchElementException e) {
        return new ErrorDto("BOARD-API-404", e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ErrorDto wrongParameter(MethodArgumentTypeMismatchException e) {
        return new ErrorDto("BOARD-API-400", "잘못된 요청입니다.(ID는 숫자만 가능)");
    }
}
