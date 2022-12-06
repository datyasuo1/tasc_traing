package com.tass.productservice.model;

import lombok.Data;

@Data
public class ApiException extends RuntimeException{
    public ApiException(){
        super();
    }

    private int code;

    public ApiException(int code , String message){
        super(message);
        this.code = code;
    }

    public ApiException(ERROR error){
        super(error.message);
        this.code = error.code;
    }

    public ApiException(ERROR error , String message){
        super(message);
        this.code = error.code;
    }
}
