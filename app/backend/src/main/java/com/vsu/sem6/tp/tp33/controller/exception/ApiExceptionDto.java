package com.vsu.sem6.tp.tp33.controller.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ApiExceptionDto(String message,
                              HttpStatus httpStatus,
                              LocalDateTime time
) {
}
