package com.navi.nbcampauthenticationwithoutspringsecurityexample.user.model;

public record UserRegisterRequest(
    String username,
    String password
) {

}
