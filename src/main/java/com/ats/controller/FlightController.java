package com.ats.controller;

import com.ats.model.flight.Flight;
import com.ats.model.flight.FlightInput;
import com.ats.service.AirlineService;
import com.ats.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flights")
@PreAuthorize("hasRole('Admin')")
public class FlightController {
    FlightService flightService;
    AirlineService airlineService;

    @Autowired
    public FlightController(FlightService flightService, AirlineService airlineService) {
        this.flightService = flightService;
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody FlightInput flightInput){
        return new ResponseEntity<>(flightService.addFlight(flightInput), HttpStatus.CREATED);
    }

    @GetMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Flight>> getAllFlight(){
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping(value = "/searched-flights")
    @PreAuthorize("permitAll()")
    public ResponseEntity<List<Flight>> getFilteredFlights(@RequestBody Map<String, String> filterFields){
        List<Flight> flights = flightService.getFilteredFlights(filterFields);
        return ResponseEntity.ok(flights);
    }

    @PutMapping(value = "/{flightId}")
    public ResponseEntity<Flight> updateFlight(@PathVariable int flightId, @RequestBody FlightInput flightInput){
        Flight modifiedFlight = flightService.updateFlight(flightId, flightInput);

        return ResponseEntity.ok(modifiedFlight);
    }

    @DeleteMapping(value = "/{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable int flightId){
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }
}
