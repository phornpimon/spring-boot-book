package com.pornpimon.stockbackend.service;

import com.pornpimon.stockbackend.controller.request.UserRequest;
import com.pornpimon.stockbackend.model.User;

public interface UserService {
    
    User register(UserRequest userRequest);

    User findUserByUsername(String username);

    User getProfileById(Long id);
}
