package com.pornpimon.stockbackend.service;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pornpimon.stockbackend.Repository.UserRepository;
import com.pornpimon.stockbackend.controller.request.UserRequest;
import com.pornpimon.stockbackend.exception.UserDuplicateException;
import com.pornpimon.stockbackend.model.User;

@Service
public class UserServiceImpl implements UserService {

    private final StoregeService storegeService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(
            StoregeService storegeService,
            UserRepository userRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.storegeService = storegeService;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User register(UserRequest userRequest) {
        User user = userRepository.findByUsername(userRequest.getUsername());
        if (user == null) {
            String fileName = storegeService.store(userRequest.getImage());
            user = new User()
                    .setUsername(userRequest.getUsername())
                    .setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
                    .setRole(userRequest.getRole())
                    .setName(userRequest.getName())
                    .setAge(userRequest.getAge())
                    .setAddress(userRequest.getAddress())
                    .setTelephone(userRequest.getTelephone())
                    .setImage(fileName);
            return userRepository.save(user);
        }
        throw new UserDuplicateException(userRequest.getUsername());
    }

    @Override
    public User findUserByUsername(String username) {
        Optional<User> user = Optional.ofNullable(userRepository.findByUsername(username));
        if (user.isPresent()) {
            return user.get();
        }
        return null;
    }

}
