package com.ats.controller;

import com.ats.model.jwt.JWTRequest;
import com.ats.model.jwt.RefreshTokenResponse;
import com.ats.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class LoginController {
    private final LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("users/login")
    public ResponseEntity<RefreshTokenResponse> login(@Valid @RequestBody JWTRequest request) {
        return ResponseEntity.ok(loginService.authenticate(request));
    }
}
