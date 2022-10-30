package com.pornpimon.stockbackend.security;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

public class CustomAbstractHttpConfigurer extends AbstractHttpConfigurer<CustomAbstractHttpConfigurer, HttpSecurity>{

    @Override
    public void configure(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        httpSecurity
            .addFilter(new JWTAuthenticationFilter(authenticationManager))
            .addFilter(new JWTAuthorizationFilter(authenticationManager));
    }
    
}
