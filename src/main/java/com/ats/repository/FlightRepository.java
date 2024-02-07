package com.ats.repository;

import com.ats.model.flight.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    List<Flight> findAllByDepartureLocationAndArrivalLocationAndDepartureDate(
            String departureLocation,
            String arrivalLocation,
            LocalDate departureDate);
}
