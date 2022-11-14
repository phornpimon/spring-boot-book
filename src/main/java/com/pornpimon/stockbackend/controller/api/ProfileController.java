package com.pornpimon.stockbackend.controller.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pornpimon.stockbackend.model.User;
import com.pornpimon.stockbackend.service.UserService;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final UserService userService;

    public ProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public User getProfilebyId(@PathVariable long id) {
        return userService.getProfileById(id);
    }

    @GetMapping("/")
    public User getProfilebyUsername(@RequestParam String username) {
        return userService.findUserByUsername(username);
    }
    
}
