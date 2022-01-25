package com.kamal.challenge.exception;

import com.kamal.challenge.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(BadRequestException.class)
  public ResponseEntity<Object> handleBadRequestException(
      RuntimeException exception, WebRequest request) {
    log.error("[BadRequestException]: Message: {}, Exception: {}", exception.getMessage(), exception);

    return handleExceptionInternal(
        exception, exception.getMessage(), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }
}
