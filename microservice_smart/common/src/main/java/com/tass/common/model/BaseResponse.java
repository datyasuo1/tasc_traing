package com.tass.common.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(example  ="{\n" +
        "    \"code\": 1,\n" +
        "    \"message\": \"SUCCESS\"\n" +
        "}")
public class BaseResponse {

    @Schema(description = "code kết quả logic của api, mặc định là 1 = thành công " , example = "1")
    private int code;

    @Schema(description = "code kết quả logic của api, mặc định là OK = thành công " , example = "OK")
    private String errorCode;

    @Schema(description = "message của api , mặc định thành công là SUCCESS" , example = "SUCCESS")
    private String message;

    public BaseResponse(){
        this.code = 1;
        this.errorCode = "OK";
        this.message = "SUCCESS";
    }

    public BaseResponse(int code , String message){
        this.code = code;
        this.errorCode = "OK";
        this.message = message;
    }

    public BaseResponse(int code , String message, String errorCode){
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
    }

    public BaseResponse(ERROR error){
        this.code = error.code;
        this.errorCode = error.errorCode;
        this.message = error.message;
    }
    public void setMessage(ERROR error){
        this.code = error.code;
        this.errorCode = error.errorCode;
        this.message = error.message;
    }

    public void setMessage(String error){
        this.message = error;
    }



    public void setCode(ERROR error){
        this.code = error.code;
        this.message = error.message;
    }
    public void setCode(int code){
        this.code = code;

    }


    public void setErrorCode(String code){
        this.errorCode = code;

    }
    public boolean isSuccess(){
        return this.code == ERROR.SUCCESS.code;
    }
}
