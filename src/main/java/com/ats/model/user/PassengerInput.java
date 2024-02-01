package com.ats.model.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PassengerInput extends UserInput{
    @NotEmpty(message = "Please provide a valid Passport Number")
    private String passengerPassport;
}
