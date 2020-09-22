package com.github.tddiaz.springcognito.authservice.services;

import com.github.tddiaz.springcognito.authservice.dtos.SignUpRequest;
import com.github.tddiaz.springcognito.authservice.services.auth.CognitoAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpUseCase {

    private final CognitoAuthClient authClient;

    public void execute(SignUpRequest signup) {
        authClient.signup(signup);
    }

}
