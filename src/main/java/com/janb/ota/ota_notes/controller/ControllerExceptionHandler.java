package com.janb.ota.ota_notes.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ControllerExceptionHandler {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ResponseEntity<Map<String, List<String>>> handle(MethodArgumentNotValidException e) {
    List<String> errors = e.getBindingResult().getFieldErrors().stream()
        .map(error -> "'" + error.getField() + "' " + error.getDefaultMessage())
        .toList();
    return new ResponseEntity<>(Map.of("errors", errors), HttpStatus.BAD_REQUEST);
  }
}
