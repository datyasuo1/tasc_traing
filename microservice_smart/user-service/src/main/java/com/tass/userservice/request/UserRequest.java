package com.tass.userservice.request;

import com.tass.common.model.myenums.UserStatus;
import com.tass.userservice.entities.Role;
import com.tass.userservice.entities.User;
import lombok.Data;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Data
public class UserRequest {
    @NotEmpty(message = "username missing")
    private String username;
    @NotEmpty(message = "password missing")
    private String password;
    @NotEmpty(message = "password repeat missing")
    private String rePassword;
    private String avatar;
    @NotEmpty(message = "fullName missing")
    private String fullName;
    @NotEmpty(message = "phone missing")
    private String phone;
    @Email(message = "Email is not valid", regexp = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])")
    @NotEmpty(message = "Email cannot be empty")
    @Email(message = "email wrong")
    private String email;
    @Enumerated(EnumType.ORDINAL)
    private UserStatus status;
    private Set<Role> roles;


//    public UserRequest(User save) {
//    }
}
