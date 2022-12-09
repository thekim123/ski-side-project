package com.ski.backend.handler;

import com.ski.backend.handler.ex.CustomApiException;
import com.ski.backend.web.dto.CmRespDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(CustomApiException.class)
    public ResponseEntity<?> apiException(CustomApiException e) {
        return new ResponseEntity<>(new CmRespDto(-1, e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
