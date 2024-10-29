package com.navi.nbcampauthenticationwithoutspringsecurityexample.auth.model;

public record LoginRequest(
    String username,
    String password
) {

}
