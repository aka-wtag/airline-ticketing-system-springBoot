package com.ats.controller;


import com.ats.model.FactoryObjectMapper;
import com.ats.model.user.CreatePassengerDto;
import com.ats.model.user.PassengerOutputDto;
import com.ats.model.user.UpdatePassengerDto;
import com.ats.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/passengers")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<PassengerOutputDto> registrationDetails(@Valid @RequestBody CreatePassengerDto createPassengerDto) {
        PassengerOutputDto savedPassenger = userService.registerPassenger(createPassengerDto);
        return new ResponseEntity<>(savedPassenger, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{passengerId}")
    public ResponseEntity<PassengerOutputDto> editPassengerDetails(@PathVariable int passengerId,
                                                                   @Valid @RequestBody UpdatePassengerDto updatePassengerDto) {
        PassengerOutputDto modifiedPassenger = userService.updatePassengerDetails(passengerId, updatePassengerDto);
        return ResponseEntity.ok(modifiedPassenger);
    }

    @DeleteMapping(value = "/{passengerId}")
    public ResponseEntity<Void> deletePassenger(@PathVariable int passengerId) {
        userService.deletePassenger(passengerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<PassengerOutputDto>> getPassengers(){
        List<PassengerOutputDto> passengers = userService.getAllPassengers();
        return ResponseEntity.ok(passengers);
    }

    @GetMapping(value = "/{passengerId}")
    public ResponseEntity<PassengerOutputDto> getPassenger(@PathVariable int passengerId){
        PassengerOutputDto passenger = FactoryObjectMapper.convertPassengerEntityToPassengerOutput(userService.getPassenger(passengerId));
        return ResponseEntity.ok(passenger);
    }
}
