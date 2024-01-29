package com.ats.controller;


import com.ats.model.booking.BookingInput;
import com.ats.model.booking.BookingOutput;
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
@RequestMapping("/passengers")
@PreAuthorize("hasRole('Passenger')")
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

    @PostMapping(value = "/{passengerId}/bookings")
    public ResponseEntity<BookingOutput> bookTicket(@PathVariable int passengerId, @Valid @RequestBody BookingInput bookingInput) {
        return new ResponseEntity<>(bookingService.bookTicket(passengerId, bookingInput), HttpStatus.CREATED);
    }

    @GetMapping(value = "/{passengerId}/bookings")
    @PreAuthorize("hasAnyRole({'Admin', 'Passenger'})")
    public ResponseEntity<List<BookingOutput>> getBookings(@PathVariable int passengerId){
        return ResponseEntity.ok(bookingService.getBookings(passengerId));
    }


    @GetMapping(value = "/bookings")
    @PreAuthorize("hasAnyRole({'Admin', 'Passenger'})")
    public ResponseEntity<List<BookingOutput>> getAllBookings(){
        List<BookingOutput> bookings = bookingService.getAllBookings();
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping(value = "{passengerId}/bookings/{bookingId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int passengerId, @PathVariable int bookingId){
        bookingService.deleteTicket(passengerId, bookingId);
        return ResponseEntity.noContent().build();
    }
}
