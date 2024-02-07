package com.ats.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
public abstract class UpdateUserDto {
    private String userFullName;
    private String userPassword;
    @Email
    private String userEmail;
    private String userContact;
}