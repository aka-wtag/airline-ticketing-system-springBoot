package com.ats.model.airline;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CreateAirlineDto {
    @NotEmpty(message = "Airline model must be provided")
    private String airlineModel;

    @NotEmpty(message = "Airline name must be provided")
    private String airlineName;

    @Min(value = 1, message = "Number of seats should be greater than zero")
    @NotNull(message = "Airline seats must be provided")
    private Integer numberOfSeats;
}
