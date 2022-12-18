package com.example.smart_shop.controller.Admin;

import com.example.smart_shop.controller.BaseController;
import com.example.smart_shop.databases.entities.*;
import com.example.smart_shop.databases.myenum.UserStatus;
import com.example.smart_shop.model.response.ApiException;


import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.service.UserService;
import com.example.smart_shop.specification.Specifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/admins/user")
public class UsersController extends BaseController {
    @Autowired
    UserService userService;

    @GetMapping
    public Page<User> findAll(@RequestParam(value = "username", required = false) String username,
                              @RequestParam(value = "role", required = false) Role role,
                              @RequestParam(value = "fullName", required = false) String fullName,
                              @RequestParam(value = "email", required = false) String email,
                              @RequestParam(value = "phone", required = false) String phone,
                              @RequestParam(value = "address", required = false) String address,
                              @RequestParam(value = "gender", required = false) String gender,
                              @RequestParam(value = "status", required = false) UserStatus status,
                              @RequestParam(name = "page", defaultValue = "0") int page,
                              @RequestParam(name = "limit", defaultValue = "10") int limit
    ) throws ApiException {
        Specification<User> specification = Specifications.userSpec(username, role, fullName, email, phone, address, gender, status, page, limit);
        return userService.findAllAndSearch(specification, page, limit);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<BaseResponse> findById (@PathVariable  Long id) throws ApiException{
        return createdResponse(userService.findById(id));
    }

    @PutMapping(path = "/block/{id}")
    public ResponseEntity<BaseResponse> blockUser(@PathVariable Long id) throws ApiException{
        return createdResponse(userService.blockUser(id));
    }
    @PutMapping(path = "/unlock/{id}")
    public ResponseEntity<BaseResponse> unlockUser(@PathVariable Long id) throws ApiException{
        return createdResponse(userService.unlockUser(id));
    }


}
