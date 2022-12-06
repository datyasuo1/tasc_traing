package com.tass.productservice.aop;


import com.tass.productservice.model.ApiException;
import com.tass.productservice.model.BaseResponse;
import com.tass.productservice.model.ERROR;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleCustomizedException(ApiException e) {
        return new ResponseEntity<>(new BaseResponse(e.getCode(), e.getMessage()), HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleInternalException(Exception ex) {
        return new ResponseEntity<>(new BaseResponse(ERROR.SYSTEM_ERROR),
            HttpStatus.INTERNAL_SERVER_ERROR);
    }
}