package com.tass.apigw.model;

import com.tass.apigw.security.UserDetailExtend;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.ArrayList;

public class TassUserAuthentication extends UsernamePasswordAuthenticationToken {

    public TassUserAuthentication(UserDetailExtend userDetailExtend  ) {
        super(userDetailExtend, null , new ArrayList<>());
    }
}
