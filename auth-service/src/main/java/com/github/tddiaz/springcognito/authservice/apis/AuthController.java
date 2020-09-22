package com.github.tddiaz.springcognito.authservice.apis;

import com.github.tddiaz.springcognito.authservice.dtos.*;
import com.github.tddiaz.springcognito.authservice.services.ChangePasswordUseCase;
import com.github.tddiaz.springcognito.authservice.services.LoginUseCase;
import com.github.tddiaz.springcognito.authservice.services.LogoutUseCase;
import com.github.tddiaz.springcognito.authservice.services.SignUpUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;
    private final LogoutUseCase logoutUseCase;
    private final ChangePasswordUseCase changePasswordUseCase;

    @PostMapping("/sign-up")
    public void signup(@RequestBody SignUpRequest signUp) {
        signUpUseCase.execute(signUp);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest login) {
        return loginUseCase.execute(login);
    }

    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String bearerToken) {
        logoutUseCase.execute(bearerToken);
    }

    @PostMapping("/change-pass")
    public void changePass(@RequestBody ChangePassRequest changePassRequest,
                           @RequestHeader("Authorization") String bearerToken) {
        changePasswordUseCase.execute(changePassRequest, bearerToken);
    }

}
