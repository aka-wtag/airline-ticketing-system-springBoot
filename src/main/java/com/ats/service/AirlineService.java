package com.ats.service;

import com.ats.model.airline.Airline;
import com.ats.model.airline.CreateAirlineDto;
import com.ats.model.airline.UpdateAirlineDto;

import java.util.List;

public interface AirlineService {
    Airline addAirline(CreateAirlineDto createAirlineDto);
    Airline updateAirline(int airlineId, UpdateAirlineDto updateAirlineDto);
    void deleteAirline(int airlineId);
    List<Airline> getAllAirline();
    Airline getAirline(int airlineId);
}
