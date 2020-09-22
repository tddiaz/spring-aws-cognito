package com.github.tddiaz.springcognito.authservice.services;

import com.github.tddiaz.springcognito.authservice.services.auth.CognitoAuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LogoutUseCase {

    private final CognitoAuthClient authClient;

    public void execute(String accessToken) {
        authClient.logout(accessToken.replace("Bearer ", ""));
    }
}
