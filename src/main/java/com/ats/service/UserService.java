package com.ats.service;

import com.ats.model.user.Passenger;
import com.ats.model.user.PassengerInput;
import com.ats.model.user.PassengerOutput;
import com.ats.model.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    PassengerOutput registerPassenger(PassengerInput passengerInput);
    PassengerOutput updatePassengerDetails(int passengerId, PassengerInput passengerInput);
    Passenger getPassenger(int passengerId);
    User loadUserByUsername(String userEmail);
}
