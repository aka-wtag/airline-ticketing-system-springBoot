package com.ats.controller;

import com.ats.model.jwt.RefreshTokenResponse;
import com.ats.service.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RefreshTokenController {
    private final RefreshTokenService refreshTokenService;

    @Autowired
    public RefreshTokenController(RefreshTokenService refreshTokenService) {
        this.refreshTokenService = refreshTokenService;
    }

    @GetMapping("/users/refresh-token")
    public ResponseEntity<RefreshTokenResponse> refreshToken() {
        return ResponseEntity.ok(refreshTokenService.createTokenFromRefreshToken());
    }
}
