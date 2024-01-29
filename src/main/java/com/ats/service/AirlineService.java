package com.ats.service;

import com.ats.model.airline.Airline;
import com.ats.model.airline.AirlineInput;

import java.util.List;

public interface AirlineService {
    Airline addAirline(AirlineInput airlineInput);
    Airline updateAirline(String airlineId, AirlineInput airlineInput);
    void deleteAirline(String airlineId);
    List<Airline> getAllAirline();
    Airline getAirline(String airlineId);
}
