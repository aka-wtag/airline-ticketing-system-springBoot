package com.ats.service;

import com.ats.model.jwt.JWTRequest;
import com.ats.model.jwt.RefreshTokenResponse;
import com.ats.model.user.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public LoginService(AuthenticationManager authenticationManager, UserService userService, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtService = jwtService;
    }

    public RefreshTokenResponse authenticate(JWTRequest request) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());

        try {
            authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Bad credentials");
        }

        User user = userService.loadUserByUsername(request.getEmail());

        String accessToken = this.jwtService.generateAccessToken(String.valueOf(user.getUserId()), user.getClass().getSimpleName());
        String refreshToken = this.jwtService.generateRefreshToken(String.valueOf(user.getUserId()), user.getClass().getSimpleName());

        return RefreshTokenResponse
                .builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
