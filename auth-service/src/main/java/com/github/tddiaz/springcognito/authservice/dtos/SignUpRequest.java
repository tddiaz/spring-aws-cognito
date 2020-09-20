package com.github.tddiaz.springcognito.authservice.dtos;

import lombok.Data;

@Data
public class SignUpRequest {
    private String email;
    private String mobileNumber;
    private String name;
    private String password;
    private String nationality;
}
