package com.ats.service;

import com.ats.exception.BadRequestException;
import com.ats.exception.ObjectNotFoundException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.airline.Airline;
import com.ats.model.airline.CreateAirlineDto;
import com.ats.model.airline.UpdateAirlineDto;
import com.ats.repository.AirlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
      Airline lastAirline = airlineRepository.findFirstByAirlineNameOrderByAirlineIdDesc(createAirlineDto.getAirlineName());
      String lastAirlineModel = lastAirline != null ? lastAirline.getAirlineModel() : null;
      Airline airline = FactoryObjectMapper.convertAirlineInputToModel(createAirlineDto, lastAirlineModel);

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

        if(!fetchedAirline.getFlights().isEmpty()){
            throw new BadRequestException("Airline has associated flights");
        }

        airlineRepository.delete(fetchedAirline);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Airline> getAirlines(Pageable pageable) {
        return airlineRepository.findAllByOrderByAirlineIdDesc(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Airline getAirline(int airlineId) {
        return airlineRepository.findById(airlineId).orElseThrow(() -> new ObjectNotFoundException("Airline " + airlineId + " not found"));
    }
}
