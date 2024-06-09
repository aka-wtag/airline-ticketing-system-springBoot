package com.ats.service;

import com.ats.model.airline.Airline;
import com.ats.model.airline.CreateAirlineDto;
import com.ats.model.airline.UpdateAirlineDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AirlineService {
    Airline addAirline(CreateAirlineDto createAirlineDto);
    Airline updateAirline(int airlineId, UpdateAirlineDto updateAirlineDto);
    void deleteAirline(int airlineId);
//    List<Airline> getAllAirline();
    Page<Airline> getAirlines(Pageable pageable); // Change this line
    Airline getAirline(int airlineId);
}
