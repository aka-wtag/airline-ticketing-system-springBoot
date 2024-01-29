package com.ats.service;

import com.ats.model.flight.Flight;
import com.ats.model.flight.FlightInput;

import java.util.List;
import java.util.Map;

public interface FlightService {
    Flight addFlight(FlightInput flightInput);
    Flight getFlight(int flightId);
    List<Flight> getAllFlights();
    List<Flight> getFilteredFlights(Map<String, String> filterFields);
    Flight updateFlight(int flightId, FlightInput flight);
    void updateFlightAfterBooking(Flight flight, int bookedSeats);
    void updateFlightForBookingRemoved(Flight flight, int bookedSeats);
    void deleteFlight(int flightId);
}
