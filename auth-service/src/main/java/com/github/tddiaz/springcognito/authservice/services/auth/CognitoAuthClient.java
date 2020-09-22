package com.github.tddiaz.springcognito.authservice.services.auth;

import com.amazonaws.services.cognitoidp.AWSCognitoIdentityProvider;
import com.amazonaws.services.cognitoidp.model.*;
import com.github.tddiaz.springcognito.authservice.dtos.ChangePassRequest;
import com.github.tddiaz.springcognito.authservice.dtos.LoginRequest;
import com.github.tddiaz.springcognito.authservice.dtos.SignUpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class CognitoAuthClient {

    @Value("${cognito.user-pool-id}")
    private String userPoolId;

    @Value("${cognito.app-client-id}")
    private String appClientId;

    private final AWSCognitoIdentityProvider cognitoProviderClient;

    public void signup(SignUpRequest signUp) {

        // Note: after creating new user, before you can authenticate, its always mandatory to change its password.
        final AdminCreateUserRequest signUpRequest = new AdminCreateUserRequest()
                .withUserPoolId(userPoolId)
                .withTemporaryPassword(signUp.getPassword())
                .withUsername(signUp.getEmail())
                .withUserAttributes(
                        new AttributeType().withName("name").withValue(signUp.getName()),
                        new AttributeType().withName("custom:custom:nationality").withValue(signUp.getNationality()),
                        new AttributeType().withName("email").withValue(signUp.getEmail()),
                        new AttributeType().withName("email_verified").withValue("true"),
                        new AttributeType().withName("phone_number").withValue(signUp.getMobileNumber()),
                        new AttributeType().withName("phone_number_verified").withValue("true"))
                .withMessageAction(MessageActionType.SUPPRESS);

        AdminCreateUserResult createUserResult =  cognitoProviderClient.adminCreateUser(signUpRequest);
        log.info("Created User id: {}", createUserResult.getUser().getUsername());

        // add user to group
        AdminAddUserToGroupRequest addUserToGroupRequest = new AdminAddUserToGroupRequest()
                .withGroupName("group-1")
                .withUserPoolId(userPoolId)
                .withUsername(signUpRequest.getUsername());

        cognitoProviderClient.adminAddUserToGroup(addUserToGroupRequest);

        // set permanent password to make user status as CONFIRMED
        AdminSetUserPasswordRequest adminSetUserPasswordRequest = new AdminSetUserPasswordRequest()
                .withUsername(signUp.getEmail())
                .withPassword(signUp.getPassword())
                .withUserPoolId(userPoolId)
                .withPermanent(true);

        cognitoProviderClient.adminSetUserPassword(adminSetUserPasswordRequest);
    }

    public AuthenticationResultType login(LoginRequest loginRequest) {

        final Map<String, String> authParams = new HashMap<>();
        authParams.put("USERNAME", loginRequest.getEmail());
        authParams.put("PASSWORD", loginRequest.getPassword());

        final AdminInitiateAuthRequest authRequest = new AdminInitiateAuthRequest()
                .withAuthFlow(AuthFlowType.ADMIN_USER_PASSWORD_AUTH)
                .withClientId(appClientId)
                .withUserPoolId(userPoolId)
                .withAuthParameters(authParams);

        AdminInitiateAuthResult adminInitiateAuthResult = cognitoProviderClient.adminInitiateAuth(authRequest);

        return adminInitiateAuthResult.getAuthenticationResult();
    }

    public void logout(String accessToken) {
        cognitoProviderClient.globalSignOut(new GlobalSignOutRequest().withAccessToken(accessToken));
    }

    public void changePassword(ChangePassRequest changePass, String accessToken) {
        cognitoProviderClient.changePassword(new ChangePasswordRequest()
                .withAccessToken(accessToken)
                .withPreviousPassword(changePass.getOldPassword())
                .withProposedPassword(changePass.getNewPassword()));
    }

}
