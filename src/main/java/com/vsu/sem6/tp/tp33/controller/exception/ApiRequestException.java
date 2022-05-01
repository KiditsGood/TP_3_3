package com.vsu.sem6.tp.tp33.controller.exception;

public class ApiRequestException extends RuntimeException{

  public ApiRequestException(String message) {
    super(message);
  }

  public ApiRequestException(String message, Throwable cause) {
    super(message, cause);
  }
}
