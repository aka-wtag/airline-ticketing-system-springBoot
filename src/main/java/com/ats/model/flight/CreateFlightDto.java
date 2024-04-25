package com.ats.model.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class CreateFlightDto {
    @FutureOrPresent(message = "The date must be in the future.")
    @NotNull(message = "Departure Date must be provided")
    private LocalDate departureDate;

    @NotNull(message = "Departure Time must be provided")
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;

    @FutureOrPresent(message = "The date must be in the future.")
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
    private Double fare;

    @NotNull(message = "Airline ID must be provided")
    @Min(value = 1)
    private Integer airlineId;
}