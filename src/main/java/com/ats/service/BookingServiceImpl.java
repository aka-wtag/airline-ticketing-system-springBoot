package com.ats.service;

import com.ats.exception.BadRequestException;
import com.ats.exception.ObjectNotFoundException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.booking.Booking;
import com.ats.model.booking.BookingInput;
import com.ats.model.booking.BookingOutput;
import com.ats.model.flight.Flight;
import com.ats.model.user.Passenger;
import com.ats.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
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
    public BookingOutput bookTicket(int passengerId, BookingInput bookingInput) {
        Passenger passenger = userService.getPassenger(passengerId);

        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        if(!Objects.equals(passenger.getUserEmail(), principal.getName())){
            throw new BadRequestException("Not authorized to add booking to other passenger");
        }

        Flight flight = flightService.getFlight(bookingInput.getFlightId());
        Booking booking = FactoryObjectMapper.convertBookingInputToModel(bookingInput, passenger, flight);

        Flight modifiedFLight = booking.getFlight();
        flightService.updateFlightAfterBooking(modifiedFLight, booking.getBookedSeats());

        return FactoryObjectMapper.convertModelToBookingOutput(bookingRepository.save(booking));
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingOutput> getBookings(int passengerId) {
        Passenger passenger = userService.getPassenger(passengerId);

        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        if(!Objects.equals(passenger.getUserEmail(), principal.getName())){
            throw new BadRequestException("Not authorized to see others booking");
        }

        List<BookingOutput> passengerBookings = new ArrayList<>();

        for(Booking booking:  bookingRepository.findByPassenger(passenger)){
            passengerBookings.add(FactoryObjectMapper.convertModelToBookingOutput(booking));
        }

        return passengerBookings;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookingOutput> getAllBookings() {
        List<BookingOutput> passengerBookings = new ArrayList<>();

        for(Booking booking:  bookingRepository.findAll()){
            passengerBookings.add(FactoryObjectMapper.convertModelToBookingOutput(booking));
        }

        return passengerBookings;
    }

    @Override
    public void deleteTicket(int passengerId, int bookingId) {
        Booking fetchedBooking = bookingRepository.findById(bookingId).orElseThrow(() -> new ObjectNotFoundException("Booking ID-" + bookingId + " not found"));

        Principal principal = SecurityContextHolder.getContext().getAuthentication();

        if(!Objects.equals(fetchedBooking.getPassenger().getUserEmail(), principal.getName()) || fetchedBooking.getPassenger().getUserId()!=passengerId){
            throw new BadRequestException("Not authorized to delete others booking");
        }

        Flight modifiedFLight = fetchedBooking.getFlight();
        flightService.updateFlightAfterBooking(modifiedFLight, fetchedBooking.getBookedSeats());

        bookingRepository.delete(fetchedBooking);
    }
}
