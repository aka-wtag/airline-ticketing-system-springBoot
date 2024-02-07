package com.ats.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public abstract class CreateUserDto {
    @NotBlank(message = "User name must be provided")
    @Size(min = 5, max = 50, message = "Given Name is too short")
    private String userFullName;

    @NotBlank(message = "User password must be provided")
    @Size(min = 3, max = 30, message = "password length is too short")
    private String userPassword;

    @Email
    @NotBlank(message = "User email must be provided")
    private String userEmail;

    @NotBlank(message = "User contact must be provided")
    private String userContact;
}
