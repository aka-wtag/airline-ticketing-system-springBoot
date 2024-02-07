package com.ats.model.flight;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class UpdateFlightDto {
    @Min(value = 1, message = "Fare should be greater than zero")
    private Double fare;

    @Min(value = 1)
    private Integer airlineId;
}
