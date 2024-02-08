package com.ats.service;

import com.ats.exception.BadRequestException;
import com.ats.exception.ObjectNotFoundException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.airline.Airline;
import com.ats.model.flight.Flight;
import com.ats.model.flight.CreateFlightDto;
import com.ats.model.flight.UpdateFlightDto;
import com.ats.repository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
    public Flight addFlight(CreateFlightDto createFlightDto) {
        // Checking if arrival date is after departure date or not
        if (createFlightDto.getDepartureDate().isAfter(createFlightDto.getArrivalDate())){
            throw new BadRequestException("Departure Date ahead of arrival date");
        }

        // Checking if arrival time is after departure time or not
        if (createFlightDto.getDepartureDate().isEqual(createFlightDto.getArrivalDate()) && createFlightDto.getDepartureTime().isAfter(createFlightDto.getArrivalTime())){
            throw new BadRequestException("Departure Time ahead of arrival Time");
        }

        Airline airline = airlineService.getAirline(createFlightDto.getAirlineId());
        Flight flight = FactoryObjectMapper.convertFlightInputToModel(createFlightDto, airline);
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
    public List<Flight> getFilteredFlights(LocalDate departureDate, String departureLocation, String arrivalLocation) {
        return flightRepository.findAllByDepartureLocationAndArrivalLocationAndDepartureDate(
                arrivalLocation,
                departureLocation,
                departureDate);
    }

    @Override
    public Flight updateFlight(int flightId, UpdateFlightDto updateFlightDto) {
        Flight dbFlight = flightRepository.findById(flightId).orElseThrow(() -> new ObjectNotFoundException("Flight ID-" + flightId + " not found"));

        // Setting fields to database object
        if(updateFlightDto.getAirlineId()!=null){
            Airline airline = airlineService.getAirline(updateFlightDto.getAirlineId());
            dbFlight.setAirline(airline);
        }
        if(updateFlightDto.getFare()!=null) dbFlight.setFare(updateFlightDto.getFare());

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
