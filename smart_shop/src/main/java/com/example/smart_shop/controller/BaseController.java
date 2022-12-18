package com.example.smart_shop.controller;

import com.example.smart_shop.model.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController {
    public ResponseEntity<BaseResponse> createdResponse(BaseResponse response){
        return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
    }
    public ResponseEntity<BaseResponse> createdResponse(BaseResponse response, HttpStatus httpStatus){
        return new ResponseEntity<>(response , httpStatus);
    }
}
