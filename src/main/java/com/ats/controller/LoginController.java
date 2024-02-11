package com.ats.controller;

import com.ats.service.JwtService;
import com.ats.model.jwt.JWTRequest;
import com.ats.model.jwt.RefreshTokenResponse;
import com.ats.service.LoginService;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

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
