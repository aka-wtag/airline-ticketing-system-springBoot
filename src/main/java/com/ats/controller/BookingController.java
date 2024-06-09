package com.ats.controller;


import com.ats.model.booking.BookingOutputDto;
import com.ats.model.booking.CreateBookingDto;
import com.ats.service.BookingService;
import com.ats.service.FlightService;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<BookingOutputDto> bookTicket(@PathVariable int passengerId, @Valid @RequestBody CreateBookingDto createBookingDto) {
        return new ResponseEntity<>(bookingService.bookTicket(passengerId, createBookingDto), HttpStatus.CREATED);
    }

    @GetMapping(value = "/passengers/{passengerId}/bookings")
    public ResponseEntity<List<BookingOutputDto>> getBookings(@PathVariable int passengerId){
        return ResponseEntity.ok(bookingService.getBookings(passengerId));
    }


    @GetMapping(value = "/bookings")
    public ResponseEntity<Page<BookingOutputDto>> getAllBookings(@RequestParam(required = false) Integer page,
                                                                 @RequestParam(required = false) Integer size){
        Pageable pageable = null;

        if(Objects.nonNull(page) && Objects.nonNull(size)){
            pageable = PageRequest.of(page, size);
        }

        Page<BookingOutputDto> bookings = bookingService.getAllBookings(pageable);
        return ResponseEntity.ok(bookings);
    }

    @DeleteMapping(value = "/passengers/{passengerId}/bookings/{bookingId}")
    public ResponseEntity<Void> deleteTicket(@PathVariable int passengerId, @PathVariable int bookingId){
        bookingService.deleteTicket(passengerId, bookingId);
        return ResponseEntity.noContent().build();
    }
}
