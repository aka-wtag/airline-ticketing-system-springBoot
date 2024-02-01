package com.ats.service;

import com.ats.exception.BadRequestException;
import com.ats.exception.ObjectNotFoundException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.airline.Airline;
import com.ats.model.flight.Flight;
import com.ats.model.flight.FlightInput;
import com.ats.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FlightServiceImpl implements FlightService{
    private final AirlineService airlineService;
    private final FlightRepository flightRepository;

    @Autowired
    public FlightServiceImpl(FlightRepository flightRepository, AirlineService airlineService) {
        this.flightRepository = flightRepository;
        this.airlineService = airlineService;
    }

    @Override
    public Flight addFlight(FlightInput flightInput) {
        System.out.println(flightInput.getDepartureDate());
        System.out.println(flightInput.getDepartureTime());
        if (flightInput.getDepartureDate().isAfter(flightInput.getArrivalDate())){
            throw new BadRequestException("Departure Date ahead of arrival date");
        }

        if (flightInput.getDepartureDate().isEqual(flightInput.getArrivalDate()) && flightInput.getDepartureTime().isAfter(flightInput.getArrivalTime())){
            throw new BadRequestException("Departure Time ahead of arrival Time");
        }

        Airline airline = airlineService.getAirline(flightInput.getAirlineId());
        Flight flight = FactoryObjectMapper.convertFlightInputToModel(flightInput, airline);
        return flightRepository.save(flight);
    }

    @Override
    public Flight getFlight(int flightId) {
        return flightRepository.findById(flightId).orElseThrow(() -> new ObjectNotFoundException("Flight not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Flight> getFilteredFlights(Map<String, String> filterFields) {

        String departureLocation = filterFields.get("departureLocation");
        String arrivalLocation = filterFields.get("arrivalLocation");
        String departureDate = filterFields.get("departureDate");

        return flightRepository.findAllByDepartureLocationAndArrivalLocationAndDepartureDate(departureLocation, arrivalLocation, departureDate);
    }

    @Override
    public Flight updateFlight(int flightId, FlightInput flightInput) {
        Flight dbFlight = flightRepository.findById(flightId).orElseThrow(() -> new ObjectNotFoundException("Flight ID-" + flightId + " not found"));

        Airline airline = airlineService.getAirline(flightInput.getAirlineId());

        dbFlight.setFare(flightInput.getFare());
        dbFlight.setAirline(airline);
        dbFlight.setDepartureDate(flightInput.getDepartureDate());
        dbFlight.setArrivalDate(flightInput.getArrivalDate());
        dbFlight.setDepartureLocation(flightInput.getDepartureLocation());
        dbFlight.setArrivalLocation(flightInput.getArrivalLocation());
        dbFlight.setDepartureTime(flightInput.getDepartureTime());
        dbFlight.setArrivalTime(flightInput.getArrivalTime());

        return flightRepository.save(dbFlight);
    }

    @Override
    public void updateFlightAfterBooking(Flight flight, int bookedSeats) {
        flight.setRemainingSeats(flight.getRemainingSeats()-bookedSeats);
        flightRepository.save(flight);
    }

    @Override
    public void updateFlightForBookingRemoved(Flight flight, int bookedSeats) {
        flight.setRemainingSeats(flight.getRemainingSeats()+bookedSeats);
        flightRepository.save(flight);
    }

    @Override
    public void deleteFlight(int flightId) {
        Flight dbFlight = flightRepository.findById(flightId).orElseThrow(() -> new ObjectNotFoundException("Flight ID-" + flightId + " not found"));
        flightRepository.delete(dbFlight);
    }
}
