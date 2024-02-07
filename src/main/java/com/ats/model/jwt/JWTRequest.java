package com.ats.model.jwt;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class JWTRequest {
    @NotEmpty
    @Email(message = "Email must be valid")
    private String email;
    @NotEmpty
    private String password;
}
