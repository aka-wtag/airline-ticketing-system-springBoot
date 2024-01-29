package com.ats.model.booking;

import com.ats.model.flight.Flight;
import com.ats.model.user.Passenger;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Getter
@Setter
public class BookingInput {
    @Min(value = 0, message = "Number of seats need to be at least 1")
    private int bookedSeats;

    @Min(value = 1, message = "Valid flight ID must be provided")
    private int flightId;
}
