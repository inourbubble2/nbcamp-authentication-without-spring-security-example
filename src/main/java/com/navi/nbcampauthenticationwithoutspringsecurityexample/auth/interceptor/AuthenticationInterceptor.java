package com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.interceptor;

import com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.util.JwtUtil;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.entity.User;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    private final UserRepository userRepository;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String accessToken = header.substring(7);
        String subject = jwtUtil.extractSubject(accessToken);
        User user = userRepository.findById(Long.parseLong(subject))
            .orElseThrow(() -> new IllegalArgumentException("user not exists."));

        request.setAttribute("user", user);
        return true;
    }
}
