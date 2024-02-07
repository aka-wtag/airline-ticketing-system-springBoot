package com.ats.controller;

import com.ats.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyRole({'Admin', 'Passenger'})")
public class LogoutController {
    private final JwtService jwtService;

    @Autowired
    public LogoutController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/users/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        // Extract the token from the Authorization header
        String jwt = token.substring(7);

        // Invalidate the token
        jwtService.blacklistToken(jwt);

        return ResponseEntity.noContent().build();
    }
}