package com.ats.controller;


import com.ats.model.booking.CreateBookingDto;
import com.ats.model.booking.BookingOutputDto;
import com.ats.service.BookingService;
import com.ats.service.FlightService;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class BookingController {
    BookingService bookingService;
    UserService userService;
    FlightService flightService;

    @Autowired
    public BookingController(BookingService bookingService, UserService userService, FlightService flightService) {
        this.bookingService = bookingService;
        this.userService = userService;
        this.flightService = flightService;
    }

    @PostMapping(value = "/passengers/{passengerId}/bookings")
    @PreAuthorize("hasRole('Passenger')")
    public ResponseEntity<BookingOutputDto> bookTicket(@PathVariable int passengerId, @Valid @RequestBody CreateBookingDto createBookingDto) {
        return new ResponseEntity<>(bookingService.bookTicket(passengerId, createBookingDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/passengers/{passengerId}/bookings")
    @PreAuthorize("hasAnyRole({'Admin', 'Passenger'})")
    public ResponseEntity<List<BookingOutputDto>> getBookings(@PathVariable int passengerId){
        return ResponseEntity.ok(bookingService.getBookings(passengerId));
    }


    @GetMapping(value = "/bookings")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<BookingOutputDto>> getAllBookings(){
        List<BookingOutputDto> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping(value = "/passengers/{passengerId}/bookings/{bookingId}")
    @PreAuthorize("hasAnyRole({'Admin', 'Passenger'})")
    public ResponseEntity<Void> deleteTicket(@PathVariable int passengerId, @PathVariable int bookingId){
        bookingService.deleteTicket(passengerId, bookingId);
        return ResponseEntity.noContent().build();
    }
}
