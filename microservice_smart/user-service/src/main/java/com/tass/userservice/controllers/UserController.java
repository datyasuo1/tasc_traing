package com.tass.userservice.controllers;

import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.redis.dto.UserLoginDTO;
import com.tass.userservice.request.LoginRequest;
import com.tass.userservice.request.UserRequest;
import com.tass.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequestMapping("/api/v1")
public class UserController {

    @Autowired
    UserService userService;


    @PostMapping("/register")
    public BaseResponseV2 register(@RequestBody UserRequest request) throws ApplicationException {
        return userService.register(request);
//        return new BaseResponseV2<>("Hello world");
    }
    @PostMapping("/login")
    public BaseResponseV2<UserLoginDTO> login(@RequestBody LoginRequest loginRequest) throws
            ApplicationException{
        return userService.login(loginRequest);
    }
}
