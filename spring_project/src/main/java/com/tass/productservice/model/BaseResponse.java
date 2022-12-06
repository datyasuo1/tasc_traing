package com.tass.productservice.model;

import com.tass.productservice.databases.entities.Category;
import lombok.Data;

@Data
public class BaseResponse<T> {
    private int code;
    private String message;
    private  T content;

    public BaseResponse(){
        this.code = 1;
        this.message = "SUCCESS";
    }

    public BaseResponse(ERROR error){
        this.code = error.code;
        this.message = error.message;
    }

    public BaseResponse(int code, String messsage){
        this.code = code;
        this.message = messsage;
    }

    public BaseResponse(int code, String messsage,T list){
        this.code = code;
        this.message = messsage;
        this.content = list;
    }

}