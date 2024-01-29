package com.ats.model.airline;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AirlineInput {
    @NotEmpty(message = "Airline ID must be provided")
    private String airlineId;

    @NotEmpty(message = "Airline name must be provided")
    private String airlineName;

    @Min(value = 1, message = "Number of seats should be greater than zero")
    private int numberOfSeats;
}
