package com.github.tddiaz.springcognito.authservice.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String idToken;
    private String accessToken;
    private String refreshToken;
}
