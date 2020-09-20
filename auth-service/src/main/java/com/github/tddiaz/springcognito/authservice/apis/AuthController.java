package com.github.tddiaz.springcognito.authservice.apis;

import com.github.tddiaz.springcognito.authservice.dtos.LoginRequest;
import com.github.tddiaz.springcognito.authservice.dtos.LoginResponse;
import com.github.tddiaz.springcognito.authservice.dtos.SignUpRequest;
import com.github.tddiaz.springcognito.authservice.services.LoginUseCase;
import com.github.tddiaz.springcognito.authservice.services.SignUpUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;

    @PostMapping("/sign-up")
    public void signup(@RequestBody SignUpRequest signUp) {
        signUpUseCase.execute(signUp);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest login) {
        return loginUseCase.execute(login);
    }
}
