package com.vsu.sem6.tp.tp33.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class ApiExceptionHandler {

  @ExceptionHandler(value = ApiRequestException.class)
  public ResponseEntity<Object> handleApiRequestException(ApiRequestException e) {
    HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

    ApiExceptionDto apiExceptionDto = new ApiExceptionDto(
      e.getMessage(),
      httpStatus,
      LocalDateTime.now()
    );

    return new ResponseEntity<>(apiExceptionDto, httpStatus);
  }
}
