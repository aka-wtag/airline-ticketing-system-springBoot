package com.ats.model.flight;

import com.ats.model.airline.Airline;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FlightInput {
    @NotEmpty(message = "Departure Date must be provided")
    private String departureDate;

    @NotEmpty(message = "Departure Time must be provided")
    private String departureTime;

    @NotEmpty(message = "Arrival Date must be provided")
    private String arrivalDate;

    @NotEmpty(message = "Arrival Time must be provided")
    private String arrivalTime;

    @NotEmpty(message = "Departure Location must be provided")
    private String departureLocation;

    @NotEmpty(message = "Arrival Location must be provided")
    private String arrivalLocation;

    @NotNull(message = "Fare must be provided")
    @Min(value = 1, message = "Fare should be greater than zero")
    private double fare;

    @NotNull(message = "Airline ID must be provided")
    @NotEmpty(message = "Airline ID can not be empty")
    private String airlineId;
}
