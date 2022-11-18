package com.pornpimon.stockbackend.security;

public interface SecurityConstants {
    String SECRET_KEY_ = "joyDevJWS";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_AUTHORIZATION = "Authorization";
    String CLAIMS_ROLE = "role";
    long EXPIRATION_TIME = (30*60*1000);
}
