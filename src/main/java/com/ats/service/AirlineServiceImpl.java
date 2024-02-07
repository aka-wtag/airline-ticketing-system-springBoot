package com.ats.service;

import com.ats.exception.ObjectNotFoundException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.airline.Airline;
import com.ats.model.airline.CreateAirlineDto;
import com.ats.model.airline.UpdateAirlineDto;
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
    public Airline addAirline(CreateAirlineDto createAirlineDto) {
        Airline airline = FactoryObjectMapper.convertAirlineInputToModel(createAirlineDto);
        return airlineRepository.save(airline);
    }

    @Override
    public Airline updateAirline(int airlineId, UpdateAirlineDto updateAirlineDto) {
        Airline dbAirline = airlineRepository.findById(airlineId).orElseThrow(() -> new ObjectNotFoundException("Airline " + airlineId + " not found"));

        // Setting fields to database object
        if(updateAirlineDto.getAirlineModel()!=null) dbAirline.setAirlineModel(updateAirlineDto.getAirlineModel());
        if(updateAirlineDto.getAirlineName()!=null) dbAirline.setAirlineName(updateAirlineDto.getAirlineName());
        if(updateAirlineDto.getNumberOfSeats()!=null) dbAirline.setNumberOfSeats(updateAirlineDto.getNumberOfSeats());

        return airlineRepository.save(dbAirline);
    }

    @Override
    public void deleteAirline(int airlineId) {
        Airline fetchedAirline = airlineRepository.findById(airlineId).orElseThrow(() -> new ObjectNotFoundException("Airline " + airlineId + " not found"));
        airlineRepository.delete(fetchedAirline);
    }

    @Override
    public List<Airline> getAllAirline() {
        return airlineRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Airline getAirline(int airlineId) {
        return airlineRepository.findById(airlineId).orElseThrow(() -> new ObjectNotFoundException("Airline " + airlineId + " not found"));
    }
}
