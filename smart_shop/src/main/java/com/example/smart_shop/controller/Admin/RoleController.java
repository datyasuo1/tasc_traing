package com.example.smart_shop.controller.Admin;

import com.example.smart_shop.controller.BaseController;
import com.example.smart_shop.model.response.ApiException;
import com.example.smart_shop.model.response.BaseResponse;
import com.example.smart_shop.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/api/v1/admins/role")
public class RoleController extends BaseController {
    @Autowired
    RoleService roleService;

    @GetMapping
    public ResponseEntity<BaseResponse> findAll()throws ApiException {
        return createdResponse(roleService.findAll());
    }

}
