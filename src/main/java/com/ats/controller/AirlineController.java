package com.ats.controller;

import com.ats.model.airline.Airline;
import com.ats.model.airline.CreateAirlineDto;
import com.ats.model.airline.UpdateAirlineDto;
import com.ats.service.AirlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/airlines")
public class AirlineController {
    private final AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<Airline> addAirline(@Valid @RequestBody CreateAirlineDto createAirlineDto){
        return new ResponseEntity<>(airlineService.addAirline(createAirlineDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Airline>> getAirlines(){
        List<Airline> airlines = airlineService.getAllAirline();
        return ResponseEntity.ok(airlines);
    }

    @GetMapping(value = "/{airlineId}")
    public ResponseEntity<Airline> getAirline(@PathVariable int airlineId){
        Airline airline = airlineService.getAirline(airlineId);
        return ResponseEntity.ok(airline);
    }

    @PutMapping(value = "/{airlineId}")
    public ResponseEntity<Airline> updateAirline(@PathVariable int airlineId, @RequestBody UpdateAirlineDto updateAirlineDto){
        Airline modifiedAirline = airlineService.updateAirline(airlineId, updateAirlineDto);
        return ResponseEntity.ok(modifiedAirline);
    }

    @DeleteMapping(value = "/{airlineId}")
    public ResponseEntity<Void> deleteAirline(@PathVariable int airlineId){
        airlineService.deleteAirline(airlineId);
        return ResponseEntity.noContent().build();
    }
}
