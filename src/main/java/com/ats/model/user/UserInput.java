package com.ats.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class UserInput {
    @NotEmpty(message = "User name must be provided")
    private String userFullName;

    @NotEmpty(message = "User password must be provided")
    private String userPassword;


    @Email
    @NotEmpty(message = "User email must be provided")
    private String userEmail;

    @NotEmpty(message = "User contact must be provided")
    private String userContact;
}
