package com.tass.userservice.controllers.admin;

import com.tass.common.customanotation.RequireUserLogin;
import com.tass.common.model.ApplicationException;
import com.tass.common.model.BaseResponse;
import com.tass.common.model.BaseResponseV2;
import com.tass.common.model.myenums.UserStatus;
import com.tass.userservice.entities.Role;
import com.tass.userservice.entities.User;
import com.tass.userservice.services.UserService;
import com.tass.userservice.specification.Specifications;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping(path = "/admins")
public class UsersController  {
    @Autowired
    UserService userService;
    @RequireUserLogin
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
    ) throws ApplicationException {
        Specification<User> specification = Specifications.userSpec(username, role, fullName, email, phone, address, gender, status, page, limit);
        return userService.findAllAndSearch(specification, page, limit);
    }

    @RequireUserLogin
    @GetMapping(path = "/{id}")
    public BaseResponseV2 findById (@PathVariable  Long id) throws ApplicationException{
        return userService.findById(id);
    }
    @RequireUserLogin
    @PutMapping(path = "/block/{id}")
    public BaseResponseV2 blockUser(@PathVariable Long id) throws ApplicationException{
        return userService.blockUser(id);
    }
    @RequireUserLogin
    @PutMapping(path = "/unlock/{id}")
    public BaseResponseV2 unlockUser(@PathVariable Long id) throws ApplicationException{
        return userService.unlockUser(id);
    }


}
