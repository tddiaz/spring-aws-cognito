package com.github.tddiaz.springcognito.authservice.dtos;

import lombok.Data;

@Data
public class ChangePassRequest {

    private String oldPassword;
    private String newPassword;
}
