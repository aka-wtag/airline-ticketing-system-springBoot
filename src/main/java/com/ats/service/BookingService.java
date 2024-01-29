package com.ats.service;

import com.ats.model.booking.Booking;
import com.ats.model.booking.BookingInput;
import com.ats.model.booking.BookingOutput;

import java.util.List;

public interface BookingService {
    BookingOutput bookTicket(int passengerId, BookingInput bookingInput);
    List<BookingOutput> getBookings(int passengerId);
    List<BookingOutput> getAllBookings();
    void deleteTicket(int passengerId, int bookingId);
}
