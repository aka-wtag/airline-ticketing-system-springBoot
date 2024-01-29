package com.ats.controller;


import com.ats.model.user.PassengerInput;
import com.ats.model.user.PassengerOutput;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/passengers")
public class UserController {
    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @PreAuthorize("permitAll()")
    public ResponseEntity<PassengerOutput> registrationDetails(@Valid @RequestBody PassengerInput passengerInput) {
        PassengerOutput savedPassenger = userService.registerPassenger(passengerInput);
        return new ResponseEntity<>(savedPassenger, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{passengerId}")
    @PreAuthorize("hasRole('Passenger')")
    public ResponseEntity<PassengerOutput> editPassengerDetails(@PathVariable int passengerId,
                                                                @Valid @RequestBody PassengerInput passengerInput) {
        PassengerOutput modifiedPassenger = userService.updatePassengerDetails(passengerId, passengerInput);
        return ResponseEntity.ok(modifiedPassenger);
    }
}
