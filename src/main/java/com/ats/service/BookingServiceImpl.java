package com.ats.service;

import com.ats.exception.*;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.booking.Booking;
import com.ats.model.booking.CreateBookingDto;
import com.ats.model.booking.BookingOutputDto;
import com.ats.model.flight.Flight;
import com.ats.model.user.Admin;
import com.ats.model.user.Passenger;
import com.ats.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@Transactional
public class BookingServiceImpl implements BookingService{
    private final BookingRepository bookingRepository;
    private final UserService userService;
    private final FlightService flightService;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository, UserService userService, FlightService flightService) {
        this.bookingRepository = bookingRepository;
        this.flightService = flightService;
        this.userService = userService;
    }

    @Override
    public BookingOutputDto bookTicket(int passengerId, CreateBookingDto createBookingDto) {
        Passenger passenger = userService.getPassenger(passengerId);

        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        // Checking if booking is requested by the authenticated user or not
        if(!Objects.equals(passenger.getUserEmail(), principal.getName())){
            throw new BadRequestException("Not authorized to add booking to other passenger");
        }

        Flight flight = flightService.getFlight(createBookingDto.getFlightId());

        // Checking if seats are available for booking or not
        if(flight.getRemainingSeats() - createBookingDto.getBookedSeats()<0){
            throw new BadRequestException("Seats not available");
        }

        // Checking if booking time is 4 hrs before departure time or not
        if(LocalDateTime.of(flight.getDepartureDate(), flight.getDepartureTime()).isBefore(LocalDateTime.now().plusHours(4))){
            throw new BadRequestException("Time for booking is over");
        }

        Booking booking = FactoryObjectMapper.convertBookingInputToModel(createBookingDto, passenger, flight);

        Flight modifiedFLight = booking.getFlight();
        flightService.updateFlightAfterBooking(modifiedFLight, booking.getBookedSeats());

        return FactoryObjectMapper.convertModelToBookingOutput(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingOutputDto> getBookings(int passengerId) {
        Passenger passenger = userService.getPassenger(passengerId);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Checking if bookings list is requested by the authenticated user or not
        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_Admin")) &&
                !Objects.equals(passenger.getUserEmail(), authentication.getName())){
            throw new BadRequestException("Not authorized to see others booking");
        }

        List<BookingOutputDto> passengerBookings = new ArrayList<>();

        for(Booking booking:  bookingRepository.findByPassenger(passenger)){
            passengerBookings.add(FactoryObjectMapper.convertModelToBookingOutput(booking));
        }

        return passengerBookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingOutputDto> getAllBookings() {
        List<BookingOutputDto> passengerBookings = new ArrayList<>();

        for(Booking booking:  bookingRepository.findAll()){
            passengerBookings.add(FactoryObjectMapper.convertModelToBookingOutput(booking));
        }

        return passengerBookings;
    }

    @Override
    public void deleteTicket(int passengerId, int bookingId) {
        Booking fetchedBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new ObjectNotFoundException("Booking ID-" + bookingId + " not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Checking if bookings cancellation is requested by the authenticated user or not
        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_Admin"))){
            if(!Objects.equals(fetchedBooking.getPassenger().getUserEmail(), authentication.getName())
                    || fetchedBooking.getPassenger().getUserId()!=passengerId){
                throw new BadRequestException("Not authorized to delete others booking");
            }
        }
        else{
             if(fetchedBooking.getPassenger().getUserId()!=passengerId)   {
                 throw new BadRequestException("Booking ID-"+bookingId+" does not belong to Passenger ID-"+passengerId);
             }
        }


        Flight flight = fetchedBooking.getFlight();

        // Checking if booking cancellation is 12 hrs before departure time or not
        if(LocalDateTime.of(flight.getDepartureDate(), flight.getDepartureTime()).isBefore(LocalDateTime.now().plusHours(12))){
            throw new BadRequestException("Time for booking cancellation is over");
        }

        flightService.updateFlightForBookingRemoved(flight, fetchedBooking.getBookedSeats());

        bookingRepository.delete(fetchedBooking);
    }
}