package com.ats.service;

import com.ats.model.jwt.RefreshTokenResponse;
import com.ats.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
public class RefreshTokenService {
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public RefreshTokenService(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public RefreshTokenResponse createTokenFromRefreshToken(){
        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.loadUserByUsername(principal.getName());
//        if(!jwtService.validateToken(refreshTokenInputDto.getToken(), user)){
//            throw new BadRequestException("Token is not valid");
//        }

        String newAccessToken = jwtService.generateAccessToken(String.valueOf(user.getUserId()), user.getClass().getSimpleName());
        String newRefreshToken = jwtService.generateRefreshToken(String.valueOf(user.getUserId()), user.getClass().getSimpleName());

        return RefreshTokenResponse
                .builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
