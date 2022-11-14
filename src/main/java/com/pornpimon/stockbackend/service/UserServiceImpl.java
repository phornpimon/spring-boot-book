package com.pornpimon.stockbackend.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.pornpimon.stockbackend.Repository.UserRepository;
import com.pornpimon.stockbackend.controller.request.UserRequest;
import com.pornpimon.stockbackend.exception.BookNotFoundException;
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
            LocalDate birthDate = LocalDate.of(userRequest.getBirthYear(), userRequest.getBirthMonth(), userRequest.getBirthDay());
            user = new User()
                    .setUsername(userRequest.getUsername())
                    .setPassword(bCryptPasswordEncoder.encode(userRequest.getPassword()))
                    .setRole(userRequest.getRole())
                    .setName(userRequest.getName())
                    .setBirthDate(birthDate)
                    .setAddress(userRequest.getAddress())
                    .setTelephone(userRequest.getTelephone())
                    .setEmail(userRequest.getEmail())
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

    @Override
    public User getProfileById(Long id) {
        Optional<User> user =  userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        }
        throw new BookNotFoundException(id);
    }

}
