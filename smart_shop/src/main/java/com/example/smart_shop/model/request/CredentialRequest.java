package com.example.smart_shop.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@AllArgsConstructor
public class CredentialRequest {
    private String accessToken;
    private String refreshToken;
}
