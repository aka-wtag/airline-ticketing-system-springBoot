package com.ats.service;

import com.ats.exception.BadRequestException;
import com.ats.exception.ObjectNotFoundException;
import com.ats.exception.UserAlreadyExistsException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.user.Passenger;
import com.ats.model.user.PassengerInput;
import com.ats.model.user.PassengerOutput;
import com.ats.model.user.User;
import com.ats.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.Objects;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public PassengerOutput registerPassenger(PassengerInput passengerInput) {
        // Checking if user email exists
        if(Objects.nonNull(userRepository.findByUserEmail(passengerInput.getUserEmail()))){
            throw new UserAlreadyExistsException("User email already registered");
        }
        passengerInput.setUserPassword(passwordEncoder.encode(passengerInput.getUserPassword()));

        Passenger passenger = FactoryObjectMapper.convertPassengerInputToModel(passengerInput);

        return FactoryObjectMapper.convertPassengerEntityToPassengerOutput(userRepository.save(passenger));
    }

    @Override
    public PassengerOutput updatePassengerDetails(int passengerId, PassengerInput passengerInput) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        Passenger dbPassenger = (Passenger) userRepository.findByUserEmail(principal.getName());

        // Check is request is the same user
        if(dbPassenger.getUserId()!=passengerId){
            throw new BadRequestException("Not authorized to change another user data");
        }

        // Checking if user of same email exists or not
        if(!Objects.equals(passengerInput.getUserEmail(), dbPassenger.getUserEmail())){
            if(Objects.nonNull(userRepository.findByUserEmail(passengerInput.getUserEmail()))){
                throw new UserAlreadyExistsException("An user is already registered with the email.");
            }
            dbPassenger.setUserEmail(passengerInput.getUserEmail());
        }

        // Setting fields to database object
        dbPassenger.setUserFullName(passengerInput.getUserFullName());
        passengerInput.setUserPassword(passwordEncoder.encode(passengerInput.getUserPassword()));
        dbPassenger.setUserPassword(passengerInput.getUserPassword());
        dbPassenger.setUserContact(passengerInput.getUserContact());
        dbPassenger.setPassengerPassport(passengerInput.getPassengerPassport());
        return FactoryObjectMapper.convertPassengerEntityToPassengerOutput(userRepository.save(dbPassenger));
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger getPassenger(int passengerId) {
        return (Passenger) userRepository.findById(passengerId).orElseThrow(() -> new ObjectNotFoundException("Passenger doesn't not found"));
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByUserEmail(userEmail);
    }
}
