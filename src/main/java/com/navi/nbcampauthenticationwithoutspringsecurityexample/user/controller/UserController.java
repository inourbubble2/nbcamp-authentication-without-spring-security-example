package com.navi.nbcampauthenticationwithoutspringsecurityexample.user.controller;

import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.entity.User;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.model.UserRegisterRequest;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.model.UserResponse;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @PostMapping("/api/users")
    public UserResponse register(@RequestBody UserRegisterRequest request) {
        userRepository.findByUsername(request.username()).ifPresent(__ -> {
            throw new IllegalArgumentException("username already exists");
        });

        User user = userRepository.save(
            new User(request.username(), request.password())
        );

        return new UserResponse(user.getId(), user.getUsername());
    }

}