package com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.controller;

import com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.model.LoginRequest;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.util.JwtUtil;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.entity.User;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @PostMapping("/api/auth/token")
    public String login(@RequestBody LoginRequest request) {
        User user = userRepository.findByUsernameAndPassword(request.username(), request.password())
            .orElseThrow(() -> new IllegalArgumentException("user not exists"));

        return jwtUtil.generateAccessToken(user);
    }

}
