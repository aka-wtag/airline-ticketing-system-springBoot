package com.ats.repository;

import com.ats.model.airline.Airline;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AirlineRepository extends JpaRepository<Airline, Integer> {
  Page<Airline> findAllByOrderByAirlineIdDesc(Pageable pageable);

  Airline findFirstByAirlineNameOrderByAirlineIdDesc(String airlineName);
}
