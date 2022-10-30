package com.pornpimon.stockbackend.controller.request;

import java.util.Date;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Length;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest {
    
    @NotEmpty
    @Size(min = 1, max = 250)
    private String username;

    @NotEmpty
    @Length(min = 8, message = "Must be at least {min} characters")
    private String password;

    private String role;

    private String name;

    private Date birthDate;

    private String address;

    private String telephone;

    private String email;

    private MultipartFile image;
}
