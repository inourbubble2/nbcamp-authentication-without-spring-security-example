package com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.context;

import com.navi.nbcampauthenticationwithoutspringsecurityexample.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Getter
@Setter
@Component
@RequestScope
public class AuthenticationContext {

    private User principal;

}
