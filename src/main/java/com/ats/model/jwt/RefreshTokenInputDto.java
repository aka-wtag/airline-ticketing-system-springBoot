package com.ats.model.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenInputDto {
    @NotEmpty(message = "token must be provided")
    private String token;
}
