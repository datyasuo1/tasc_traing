package com.example.smart_shop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.TimeToLive;

import javax.validation.constraints.NotEmpty;
import java.util.concurrent.TimeUnit;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotEmpty(message = "Username missing")
    private String username;
    @NotEmpty(message = "Password missing")
    private String password;

    @TimeToLive(unit = TimeUnit.SECONDS)
    private long expiredTime;
}
