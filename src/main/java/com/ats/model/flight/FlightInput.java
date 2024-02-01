package com.ats.model.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class FlightInput {
    @Future(message = "The date must be in the future.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Departure Date must be provided")
    private LocalDate departureDate;

    @NotNull(message = "Departure Time must be provided")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    @Future(message = "The date must be in the future.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Arrival Date must be provided")
    private LocalDate arrivalDate;

    @NotNull(message = "Arrival Time must be provided")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;


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