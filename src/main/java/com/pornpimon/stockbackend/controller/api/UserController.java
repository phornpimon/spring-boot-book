package com.pornpimon.stockbackend.controller.api;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pornpimon.stockbackend.controller.request.UserRequest;
import com.pornpimon.stockbackend.exception.ValidationException;
import com.pornpimon.stockbackend.model.User;
import com.pornpimon.stockbackend.service.UserService;

@RequestMapping("/auth")
@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/register")
    public User register(@Valid UserRequest userRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            bindingResult.getFieldErrors().stream().forEach(filedError -> {
                throw new ValidationException(filedError.getField() + " : " + filedError.getDefaultMessage());
            });
        }
        return userService.register(userRequest);
    }
}
