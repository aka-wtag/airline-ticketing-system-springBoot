package com.ats.service;

import com.ats.model.booking.CreateBookingDto;
import com.ats.model.booking.BookingOutputDto;

import java.util.List;

public interface BookingService {
    BookingOutputDto bookTicket(int passengerId, CreateBookingDto createBookingDto);
    List<BookingOutputDto> getBookings(int passengerId);
    List<BookingOutputDto> getAllBookings();
    void deleteTicket(int passengerId, int bookingId);
}
