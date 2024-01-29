package com.ats.controller;

import com.ats.model.airline.Airline;
import com.ats.model.airline.AirlineInput;
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
@PreAuthorize("hasRole('Admin')")
public class AirlineController {
    AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<Airline> addAirline(@Valid @RequestBody AirlineInput airlineInput){
        return new ResponseEntity<>(airlineService.addAirline(airlineInput), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Airline>> getAirlines(){
        List<Airline> airlines = airlineService.getAllAirline();
        return ResponseEntity.ok(airlines);
    }

    @PutMapping(value = "/{airlineId}")
    public ResponseEntity<Airline> updateAirline(@PathVariable String airlineId, @RequestBody  AirlineInput airlineInput){
        Airline modifiedAirline = airlineService.updateAirline(airlineId, airlineInput);
        return ResponseEntity.ok(modifiedAirline);
    }

    @DeleteMapping(value = "/{airlineId}")
    public ResponseEntity<Void> deleteAirline(@PathVariable String airlineId){
        airlineService.deleteAirline(airlineId);
        return ResponseEntity.noContent().build();
    }
}
