package com.github.tddiaz.springcognito.authservice.services;

import com.github.tddiaz.springcognito.authservice.dtos.ChangePassRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChangePasswordUseCase {

    private final CognitoAuthClient authClient;

    public void execute(ChangePassRequest changePassRequest, String bearerToken) {
        authClient.changePassword(changePassRequest, bearerToken.replace("Bearer ", ""));
    }
}
