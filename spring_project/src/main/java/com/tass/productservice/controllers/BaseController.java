package com.tass.productservice.controllers;

import com.tass.productservice.databases.entities.Category;
import com.tass.productservice.model.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class BaseController  {
//    public ResponseEntity<BaseResponse> createdResponse(BaseResponse response){
//        return new ResponseEntity<>(response , HttpStatus.ACCEPTED);
//    }
    public ResponseEntity<BaseResponse> createdResponse(BaseResponse response, HttpStatus httpStatus){
        return new ResponseEntity<>(response , httpStatus);
    }

}
