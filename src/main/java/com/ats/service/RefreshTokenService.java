package com.ats.service;

import com.ats.exception.BadRequestException;
import com.ats.model.jwt.RefreshTokenInputDto;
import com.ats.model.jwt.RefreshTokenResponse;
import com.ats.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {
    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    public RefreshTokenService(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    public RefreshTokenResponse createTokenFromRefreshToken(RefreshTokenInputDto refreshTokenInputDto){
        String username = "";
        try {
            username = jwtService.getUsernameFromToken(refreshTokenInputDto.getToken());
        } catch (Exception e){
            throw new BadRequestException("Invalid token");
        }

        User user = userService.loadUserByUsername(username);

        if(!jwtService.validateToken(refreshTokenInputDto.getToken(), user)){
            throw new BadRequestException("Token is not valid");
        }

        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        return RefreshTokenResponse
                .builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .build();
    }
}
