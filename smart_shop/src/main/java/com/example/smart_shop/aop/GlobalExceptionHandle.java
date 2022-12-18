package com.example.smart_shop.aop;



import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.model.response.ERROR;
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

        ex.printStackTrace();
        return new ResponseEntity<>(new BaseResponse(ERROR.SYSTEM_ERROR),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}