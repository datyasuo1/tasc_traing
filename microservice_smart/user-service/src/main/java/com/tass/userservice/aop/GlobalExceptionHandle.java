package com.tass.userservice.aop;




import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.ERROR;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandle extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    @ResponseBody
    public ResponseEntity<BaseResponse> handleCustomizedException(ApplicationException e) {
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