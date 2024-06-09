package com.ats.service;

import com.ats.model.user.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    PassengerOutputDto registerPassenger(CreatePassengerDto createPassengerDto);
    PassengerOutputDto updatePassengerDetails(int passengerId, UpdatePassengerDto updatePassengerDto);
    Passenger getPassenger(int passengerId);
    User loadUserByUsername(String userEmail);
    User loadUserById(int userId);
    void deletePassenger(int passengerId);
    Page<PassengerOutputDto> getPassengers(Pageable pageable);
}
