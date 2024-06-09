package com.ats.repository;

import com.ats.model.airline.Airline;
import com.ats.model.flight.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Integer> {
    @Query("SELECT f FROM Flight f " +
            "WHERE (:departureLocation IS NULL OR f.departureLocation LIKE %:departureLocation%) " +
            "AND (:arrivalLocation IS NULL OR f.arrivalLocation LIKE %:arrivalLocation%) " +
            "AND (:departureDate IS NULL OR f.departureDate = :departureDate)" +
            "ORDER BY f.flightId DESC")
    List<Flight> findFlights(@Param("departureLocation") String departureLocation,
                                       @Param("arrivalLocation") String arrivalLocation,
                                       @Param("departureDate") LocalDate departureDate);

    Page<Flight> findAllByOrderByFlightIdDesc(Pageable pageable);
}
