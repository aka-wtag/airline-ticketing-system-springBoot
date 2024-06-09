package com.ats.service;

import com.ats.model.booking.CreateBookingDto;
import com.ats.model.booking.BookingOutputDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BookingService {
    BookingOutputDto bookTicket(int passengerId, CreateBookingDto createBookingDto);
    List<BookingOutputDto> getBookings(int passengerId);
    Page<BookingOutputDto> getAllBookings(Pageable pageable);
    void deleteTicket(int passengerId, int bookingId);
}
