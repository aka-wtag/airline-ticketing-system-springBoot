package com.ats.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePassengerDto extends UpdateUserDto {
    private String passengerPassport;
}
