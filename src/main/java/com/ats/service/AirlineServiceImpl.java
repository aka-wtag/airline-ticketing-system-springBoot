package com.ats.service;

import com.ats.exception.ObjectNotFoundException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.airline.Airline;
import com.ats.model.airline.AirlineInput;
import com.ats.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AirlineServiceImpl implements AirlineService {
    private final AirlineRepository airlineRepository;

    @Autowired
    public AirlineServiceImpl(AirlineRepository airlineDao) {
        this.airlineRepository = airlineDao;
    }


    @Override
    public Airline addAirline(AirlineInput airlineInput) {
        Airline airline = FactoryObjectMapper.convertAirlineInputToModel(airlineInput);
        return airlineRepository.save(airline);
    }

    @Override
    public Airline updateAirline(String airlineId, AirlineInput airlineInput) {
        Airline dbAirline = airlineRepository.findById(airlineId).orElseThrow(() -> new ObjectNotFoundException("Airline " + airlineId + " not found"));

        if(airlineInput.getAirlineName()!=null) dbAirline.setAirlineName(airlineInput.getAirlineName());
        if(airlineInput.getNumberOfSeats()!=0) dbAirline.setNumberOfSeats(airlineInput.getNumberOfSeats());
        return airlineRepository.save(dbAirline);
    }

    @Override
    public void deleteAirline(String airlineId) {
        Airline fetchedAirline = airlineRepository.findById(airlineId).orElseThrow(() -> new ObjectNotFoundException("Airline " + airlineId + " not found"));
        airlineRepository.delete(fetchedAirline);
    }

    @Override
    public List<Airline> getAllAirline() {
        return airlineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Airline getAirline(String airlineId) {
        return airlineRepository.findById(airlineId).orElseThrow(() -> new ObjectNotFoundException("Airline " + airlineId + " not found"));
    }
}
