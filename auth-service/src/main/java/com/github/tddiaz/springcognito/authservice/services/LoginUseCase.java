package com.github.tddiaz.springcognito.authservice.services;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.github.tddiaz.springcognito.authservice.dtos.LoginRequest;
import com.github.tddiaz.springcognito.authservice.dtos.LoginResponse;
import com.github.tddiaz.springcognito.authservice.services.auth.CognitoAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoginUseCase {

    private final CognitoAuthClient cognitoAuthClient;

    public LoginResponse execute(LoginRequest loginRequest) {
        AuthenticationResultType authResult = cognitoAuthClient.login(loginRequest);

        return LoginResponse.builder()
                .accessToken(authResult.getAccessToken())
                .idToken(authResult.getIdToken())
                .refreshToken(authResult.getRefreshToken())
                .build();
    }
}
