package com.ats.repository;

import com.ats.model.airline.Airline;
import com.ats.model.booking.Booking;
import com.ats.model.user.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    Page<Booking> findAllByOrderByBookingNumberDesc(Pageable pageable);
    List<Booking> findByPassenger(Passenger passenger);
}
