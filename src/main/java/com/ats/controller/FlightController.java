package com.ats.controller;

import com.ats.model.airline.Airline;
import com.ats.model.flight.CreateFlightDto;
import com.ats.model.flight.Flight;
import com.ats.model.flight.UpdateFlightDto;
import com.ats.service.AirlineService;
import com.ats.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

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
    public ResponseEntity<Page<Flight>> getFlight(@RequestParam(required = false) Integer page,
                                                  @RequestParam(required = false) Integer size){
        Pageable pageable = null;

        if(Objects.nonNull(page) && Objects.nonNull(size)){
            pageable = PageRequest.of(page, size);
        }
        Page<Flight> flights = flightService.getFlights(pageable);
        return ResponseEntity.ok(flights);
    }

    @GetMapping(value = "/searched-flights")
    public ResponseEntity<List<Flight>> getFilteredFlights(@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate departureDate,
                                                           @RequestParam(required = false) String departureLocation,
                                                           @RequestParam(required = false) String arrivalLocation){
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
