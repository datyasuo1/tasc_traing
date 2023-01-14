package com.tass.common.model;

public enum ERROR {

    // common exception : from 1 to 99
    SYSTEM_ERROR(99, "System Error" , "SYS_ERROR"),
    SUCCESS(1, "Success" , "OK"),
    INVALID_PARAM(10 , "invalid params" , "INVALID_PARAMS"),
    ID_NOT_FOUND(11 , "Id not found on database" , "ID_NOT_FOUND"),
    RESULT_IS_NULL(12 , "result NULL" , "RESULT_IS_NULL"),
    ID_NOT_NULL(13 , "Id is null in request" , "ID_NOT_NULL"),
    VALUE_TO_LARGE(14 , "Value too large for column" , "VALUE_TO_LARGE"),
    USER_NOT_FOUND(20,"Không tìm thấy thông tin người dùng","USER_NOT_FOUND"),
    USER_NOT_ACTIVE_ON_ESB(21,"Trạng thái cán bộ không hoạt động","USER_NOT_ACTIVE_ON_ESB"),
    INVALID_PASSWORD(22,"Password sai","INVALID_PASSWORD"),

    // PRODUCT ERROR
    PRODUCT_NOT_FOUND(25,"Khong co product","Product not found"),
    PRODUCT_NOT_ACTIVE(200 , "product is not active" , "PRODUCT_NOT_ACTIVE"),
    ;
    public int code;
    public String errorCode;
    public String message;

    ERROR(int code , String message , String errorCode){
        this.code = code;
        this.errorCode = errorCode;
        this.message = message;
    }
}
