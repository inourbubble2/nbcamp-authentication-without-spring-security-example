package com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.resolver;

import com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.annotation.AuthenticatedPrincipal;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.context.AuthenticationContext;
import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class AuthenticatedPrincipalArgumentResolver implements HandlerMethodArgumentResolver {

    private final AuthenticationContext authenticationContext;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticatedPrincipal.class)
            && User.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (authenticationContext.getPrincipal() == null) {
            throw new IllegalArgumentException("principal required");
        }
        return authenticationContext.getPrincipal();
    }
}
