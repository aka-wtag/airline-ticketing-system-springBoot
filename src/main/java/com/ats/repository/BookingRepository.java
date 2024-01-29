package com.ats.repository;

import com.ats.model.booking.Booking;
import com.ats.model.user.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Integer> {
    List<Booking> findByPassenger(Passenger passenger);
}
