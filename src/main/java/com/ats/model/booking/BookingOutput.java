package com.ats.model.booking;

import com.ats.model.flight.Flight;
import com.ats.model.user.PassengerOutput;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookingOutput {
    private int bookingNumber;
    private String bookingDate;
    private int bookedSeats;
    private double bookingAmount;

    private PassengerOutput passenger;
    private Flight flight;
}
