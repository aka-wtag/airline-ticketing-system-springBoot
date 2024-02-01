package com.ats.model.user;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class PassengerOutput extends UserOutput{
    private String passengerPassport;
}
