package com.example.smart_shop.model.request;

import com.example.smart_shop.databases.entities.Role;
import com.example.smart_shop.databases.myenum.UserStatus;
import lombok.*;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.security.core.userdetails.User;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterRequest {

    @NotEmpty(message = "username missing")
    private String username;
    @NotEmpty(message = "password missing")
    private String password;
    @NotEmpty(message = "password repeat missing")
    private String rePass;
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


    public RegisterRequest(User save) {
    }
}
