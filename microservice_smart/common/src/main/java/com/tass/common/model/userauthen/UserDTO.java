package com.tass.common.model.userauthen;

import lombok.Data;

@Data
public class UserDTO {
    private String token;
    private long userId;
}
