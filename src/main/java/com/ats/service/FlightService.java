package com.ats.service;

import com.ats.model.flight.Flight;
import com.ats.model.flight.CreateFlightDto;
import com.ats.model.flight.SearchFlightDto;
import com.ats.model.flight.UpdateFlightDto;

import java.util.List;
import java.util.Map;

public interface FlightService {
    Flight addFlight(CreateFlightDto createFlightDto);
    Flight getFlight(int flightId);
    List<Flight> getAllFlights();
    List<Flight> getFilteredFlights(SearchFlightDto filterFields);
    Flight updateFlight(int flightId, UpdateFlightDto updateFlightDto);
    void updateFlightAfterBooking(Flight flight, int bookedSeats);
    void updateFlightForBookingRemoved(Flight flight, int bookedSeats);
    void deleteFlight(int flightId);
}
