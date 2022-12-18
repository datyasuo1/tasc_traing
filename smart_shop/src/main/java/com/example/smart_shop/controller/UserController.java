package com.example.smart_shop.controller;

import com.example.smart_shop.databases.entities.User;
import com.example.smart_shop.model.request.LoginRequest;
import com.example.smart_shop.model.request.RegisterRequest;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/api/v1")
public class UserController extends BaseController{
    @Autowired
    UserService userService;

    @PostMapping(path = "/register")
    public ResponseEntity<BaseResponse> register(@Valid @RequestBody RegisterRequest registerRequest)throws ApiException {
        return createdResponse(userService.register(registerRequest));
    }

    @PostMapping(path = "/login")
    public ResponseEntity<BaseResponse> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletRequest request)throws ApiException {
        return createdResponse(userService.login(loginRequest,request));
    }


}
