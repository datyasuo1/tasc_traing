package com.tass.common.model;

import lombok.Data;

@Data
public class ApplicationException extends RuntimeException{
    private int code;
    private String errorCode;

    public ApplicationException(int code , String message){
        super(message);
        this.code = code;
    }

    public ApplicationException(int code , String message, String errorCode){
        super(message);
        this.code = code;
        this.errorCode = errorCode;
    }

    public ApplicationException(ERROR error , String message){
        super(message);
        this.code = error.code;
        this.errorCode = error.errorCode;
    }

    public ApplicationException(ERROR error){
        super(error.message);
        this.code = error.code;
        this.errorCode = error.errorCode;
    }

}
