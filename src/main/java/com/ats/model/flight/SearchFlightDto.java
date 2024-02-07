package com.ats.model.flight;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
public class SearchFlightDto {
    @FutureOrPresent(message = "The date must be in the future.")
    @JsonFormat(pattern = "dd-MM-yyyy")
    @NotNull(message = "Departure Date must be provided")
    private LocalDate departureDate;
    @NotEmpty(message = "Departure Location must be provided")
    private String departureLocation;
    @NotEmpty(message = "Arrival Location must be provided")
    private String arrivalLocation;
}
