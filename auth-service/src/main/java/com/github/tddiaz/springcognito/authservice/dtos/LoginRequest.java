package com.github.tddiaz.springcognito.authservice.dtos;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}
