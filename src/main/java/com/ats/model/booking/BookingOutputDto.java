package com.ats.model.booking;

import com.ats.model.flight.Flight;
import com.ats.model.user.PassengerOutputDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BookingOutputDto {
    private int bookingNumber;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime bookingDate;
    private int bookedSeats;
    private double bookingAmount;

    private PassengerOutputDto passenger;
    private Flight flight;
}
