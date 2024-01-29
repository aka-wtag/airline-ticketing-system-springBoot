package com.ats.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class PassengerInput extends UserInput{
    @NotEmpty(message = "Please provide a valid Passport Number")
    private String passengerPassport;
}
