package com.tass.userservice.controllers.admin;


import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponseV2;
import com.tass.userservice.services.RoleService;
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
@RequestMapping(path = "/admins/role")
public class RoleController {
    @Autowired
    RoleService roleService;

    @GetMapping
    public BaseResponseV2 findAll()throws ApplicationException {
        return roleService.findAll();
    }

}
