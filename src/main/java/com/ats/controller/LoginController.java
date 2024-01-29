package com.ats.controller;

import com.ats.config.jwt.JwtHelper;
import com.ats.model.jwt.JWTRequest;
import com.ats.model.jwt.JWTResponse;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@PreAuthorize("permitAll()")
public class LoginController {
    private final UserService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JwtHelper helper;

    @Autowired
    public LoginController(UserService userDetailsService, AuthenticationManager authenticationManager, JwtHelper helper) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.helper = helper;
    }

    @PostMapping("/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest request) {
        this.doAuthenticate(request.getEmail(), request.getPassword());

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = this.helper.generateToken(userDetails);

        JWTResponse response = JWTResponse.builder()
                .jwtToken(token)
                .userName(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Invalid Username or Password!!!");
        }
    }
}
