package com.pornpimon.stockbackend.security;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pornpimon.stockbackend.controller.request.UserRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import static com.pornpimon.stockbackend.security.SecurityConstants.CLAIMS_ROLE;
import static com.pornpimon.stockbackend.security.SecurityConstants.SECRET_KEY_;
import static com.pornpimon.stockbackend.security.SecurityConstants.EXPIRATION_TIME;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        this.setAuthenticationManager(authenticationManager);
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {

            UserRequest userRequest = new ObjectMapper().readValue(request.getInputStream(), UserRequest.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userRequest.getUsername(),
                            userRequest.getPassword(),
                            new ArrayList<>()));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        if (authResult.getPrincipal() != null) {
            org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult
                    .getPrincipal();

            String username = user.getUsername();
            if (username != null && username.length() > 0) {
                Claims claims = Jwts.claims().setSubject(username).setIssuer("Joy");

                List<String> roles = new ArrayList<>();
                user.getAuthorities().stream().forEach(
                        authority -> roles.add(authority.getAuthority()));
                claims.put(CLAIMS_ROLE, roles);

                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");

                Map<String, Object> responseJSON = new HashMap<>();
                responseJSON.put("token", createToken(claims));

                OutputStream out = response.getOutputStream();
                ObjectMapper mapper = new ObjectMapper();
                mapper.writerWithDefaultPrettyPrinter().writeValue(out, responseJSON);

                out.flush();
            }
        }

    }

    private String createToken(Claims claims) {
        return Jwts.builder().setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis()+ EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY_).compact();
    }

}
