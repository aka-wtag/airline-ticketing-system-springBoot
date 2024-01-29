package com.ats.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public abstract class UserInput {
    @NotEmpty(message = "User name must be provided")
    private String userFullName;

    @NotEmpty(message = "User password must be provided")
    private String userPassword;

    @NotEmpty(message = "User email must be provided")
    private String userEmail;

    @NotEmpty(message = "User contact must be provided")
    private String userContact;
}
