package com.ats.model.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class CreatePassengerDto extends CreateUserDto {
    @NotBlank(message = "Please provide a valid Passport Number")
    private String passengerPassport;
}
