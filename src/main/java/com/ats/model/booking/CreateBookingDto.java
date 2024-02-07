package com.ats.model.booking;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;

@Getter
@Setter
public class CreateBookingDto {
    @Min(value = 0, message = "Number of seats need to be at least 1")
    private int bookedSeats;

    @Min(value = 1, message = "Valid flight ID must be provided")
    private int flightId;
}
