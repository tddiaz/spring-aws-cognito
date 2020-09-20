package com.github.tddiaz.springcognito.authservice.services;

import com.amazonaws.services.cognitoidp.model.AuthenticationResultType;
import com.github.tddiaz.springcognito.authservice.dtos.LoginRequest;
import com.github.tddiaz.springcognito.authservice.dtos.LoginResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.logging.LogRecord;

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
