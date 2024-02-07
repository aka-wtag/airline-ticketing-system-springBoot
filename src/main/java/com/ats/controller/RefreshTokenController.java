package com.ats.controller;

import com.ats.service.JwtService;
import com.ats.exception.BadRequestException;
import com.ats.model.jwt.RefreshTokenInputDto;
import com.ats.model.jwt.RefreshTokenResponse;
import com.ats.model.user.User;
import com.ats.service.RefreshTokenService;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;


    @Autowired
    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/users/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken(@Valid @RequestBody RefreshTokenInputDto refreshTokenDto) {
        return ResponseEntity.ok(refreshTokenService.createTokenFromRefreshToken(refreshTokenDto));
    }


}
