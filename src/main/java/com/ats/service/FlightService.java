package com.ats.service;

import com.ats.model.flight.Flight;
import com.ats.model.flight.CreateFlightDto;
import com.ats.model.flight.UpdateFlightDto;

import java.time.LocalDate;
import java.util.List;

public interface FlightService {
    Flight addFlight(CreateFlightDto createFlightDto);
    Flight getFlight(int flightId);
    List<Flight> getAllFlights();
    List<Flight> getFilteredFlights(LocalDate departureDate, String departureLocation, String arrivalLocation);
    Flight updateFlight(int flightId, UpdateFlightDto updateFlightDto);
    void updateFlightAfterBooking(Flight flight, int bookedSeats);
    void updateFlightForBookingRemoved(Flight flight, int bookedSeats);
    void deleteFlight(int flightId);

}
