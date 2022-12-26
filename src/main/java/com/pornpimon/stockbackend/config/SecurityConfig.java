package com.pornpimon.stockbackend.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import com.pornpimon.stockbackend.security.CustomAbstractHttpConfigurer;
import com.pornpimon.stockbackend.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.customUserDetailsService = customUserDetailsService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.userDetailsService(customUserDetailsService)
                                    .passwordEncoder(bCryptPasswordEncoder);
                                    return authenticationManagerBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
         httpSecurity
                .cors().and()
                .csrf(csrf -> csrf.disable())
                .authorizeRequests(auth -> auth
                .antMatchers("/auth/register").permitAll()
                .antMatchers("/auth/login").permitAll()
                .antMatchers(HttpMethod.GET, "/book/*/*").permitAll()
                .antMatchers(HttpMethod.GET, "/book/sort/*/*/*").permitAll()
                .antMatchers(HttpMethod.GET, "/book/search/*/*").permitAll()
                .antMatchers(HttpMethod.GET, "/book/category/*/*").permitAll()
                .antMatchers(HttpMethod.GET, "/book/*").permitAll()
                .antMatchers(HttpMethod.POST, "/book").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.PUT, "/book/*").hasAnyAuthority("ADMIN")
                .antMatchers(HttpMethod.DELETE, "/book/*").hasAnyAuthority("ADMIN")
                        .anyRequest().authenticated())
                .exceptionHandling()
                .authenticationEntryPoint(
                        (req, res, error) -> res.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .apply(new CustomAbstractHttpConfigurer()).and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                return httpSecurity.build();
    }
}
