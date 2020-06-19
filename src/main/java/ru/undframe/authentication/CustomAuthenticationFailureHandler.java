package ru.undframe.authentication;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.AuthenticationEntryPointFailureHandler;

public class CustomAuthenticationFailureHandler extends AuthenticationEntryPointFailureHandler {

    public CustomAuthenticationFailureHandler(AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationEntryPoint);
    }
}
