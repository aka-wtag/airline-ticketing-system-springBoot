package com.ats.controller;

import com.ats.model.flight.CreateFlightDto;
import com.ats.model.flight.Flight;
import com.ats.model.flight.UpdateFlightDto;
import com.ats.service.AirlineService;
import com.ats.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    FlightService flightService;
    AirlineService airlineService;

    @Autowired
    public FlightController(FlightService flightService, AirlineService airlineService) {
        this.flightService = flightService;
        this.airlineService = airlineService;
    }

    @PostMapping
    public ResponseEntity<Flight> addFlight(@Valid @RequestBody CreateFlightDto createFlightDto){
        return new ResponseEntity<>(flightService.addFlight(createFlightDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlight(){
        List<Flight> flights = flightService.getAllFlights();
        return ResponseEntity.ok(flights);
    }

    @GetMapping(value = "/searched-flights")
    public ResponseEntity<List<Flight>> getFilteredFlights(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate departureDate,
                                                           @RequestParam String departureLocation,
                                                           @RequestParam String arrivalLocation){
        List<Flight> flights = flightService.getFilteredFlights(departureDate, arrivalLocation, departureLocation);
        return ResponseEntity.ok(flights);
    }

    @PutMapping(value = "/{flightId}")
    public ResponseEntity<Flight> updateFlight(@PathVariable int flightId, @RequestBody UpdateFlightDto updateFlightDto){
        Flight modifiedFlight = flightService.updateFlight(flightId, updateFlightDto);
        return ResponseEntity.ok(modifiedFlight);
    }

    @DeleteMapping(value = "/{flightId}")
    public ResponseEntity<Void> deleteFlight(@PathVariable int flightId){
        flightService.deleteFlight(flightId);
        return ResponseEntity.noContent().build();
    }
}
