package com.ats.service;

import com.ats.exception.BadRequestException;
import com.ats.exception.ObjectNotFoundException;
import com.ats.exception.UserAlreadyExistsException;
import com.ats.model.FactoryObjectMapper;
import com.ats.model.user.*;
import com.ats.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
    public PassengerOutputDto registerPassenger(CreatePassengerDto createPassengerDto) {
        // Checking if user email exists
        if(Objects.nonNull(userRepository.findByUserEmail(createPassengerDto.getUserEmail()))){
            throw new UserAlreadyExistsException("User email already registered");
        }

        createPassengerDto.setUserPassword(passwordEncoder.encode(createPassengerDto.getUserPassword()));

        Passenger passenger = FactoryObjectMapper.convertPassengerInputToModel(createPassengerDto);

        return FactoryObjectMapper.convertPassengerEntityToPassengerOutput(userRepository.save(passenger));
    }

    @Override
    public PassengerOutputDto updatePassengerDetails(int passengerId, UpdatePassengerDto updatePassengerDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Passenger dbPassenger = (Passenger) userRepository.findById(passengerId).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        // Checking if request from the authenticated user
        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_Admin")) &&
                !Objects.equals(dbPassenger.getUserEmail(), authentication.getName())){
            throw new BadRequestException("Not authorized to change another user data");
        }

        // Checking if user of same email exists or not
        if(Objects.nonNull(updatePassengerDto.getUserEmail()) && !Objects.equals(updatePassengerDto.getUserEmail(), dbPassenger.getUserEmail())){
            if(Objects.nonNull(userRepository.findByUserEmail(updatePassengerDto.getUserEmail()))){
                throw new UserAlreadyExistsException("An user is already registered with the email.");
            }
            dbPassenger.setUserEmail(updatePassengerDto.getUserEmail());
        }

        // Setting fields to database object
        if (Objects.nonNull(updatePassengerDto.getUserFullName())) dbPassenger.setUserFullName(updatePassengerDto.getUserFullName());
        if (Objects.nonNull(updatePassengerDto.getUserPassword())){
            updatePassengerDto.setUserPassword(passwordEncoder.encode(updatePassengerDto.getUserPassword()));
            dbPassenger.setUserPassword(updatePassengerDto.getUserPassword());
        }
        if (Objects.nonNull(updatePassengerDto.getUserContact())) dbPassenger.setUserContact(updatePassengerDto.getUserContact());
        if (Objects.nonNull(updatePassengerDto.getPassengerPassport())) dbPassenger.setPassengerPassport(updatePassengerDto.getPassengerPassport());

        return FactoryObjectMapper.convertPassengerEntityToPassengerOutput(userRepository.save(dbPassenger));
    }

    @Override
    @Transactional(readOnly = true)
    public Passenger getPassenger(int passengerId) {
        User user = userRepository.findById(passengerId).orElseThrow(() -> new ObjectNotFoundException("User not found"));
        if(user instanceof Passenger){
            return (Passenger) user;
        }
        throw new ObjectNotFoundException("Passenger not found");
    }

    @Override
    @Transactional(readOnly = true)
    public User loadUserByUsername(String userEmail) throws UsernameNotFoundException {
        return userRepository.findByUserEmail(userEmail);
    }

    @Override
    public void deletePassenger(int passengerId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Passenger dbPassenger = (Passenger) userRepository.findById(passengerId).orElseThrow(() -> new ObjectNotFoundException("User not found"));

        // Checking if request from the authenticated user
        if(authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_Admin")) &&
                !Objects.equals(dbPassenger.getUserEmail(), authentication.getName())){
            throw new BadRequestException("Not authorized to delete another user");
        }

        userRepository.delete(dbPassenger);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PassengerOutputDto> getAllPassengers() {
        List<User> users = userRepository.findAll();
        List<PassengerOutputDto> passengers = new ArrayList<>();

        for(User u: users){
            if(u instanceof Passenger) {
                passengers.add(FactoryObjectMapper.convertPassengerEntityToPassengerOutput((Passenger) u));
            }
        }

        return passengers;
    }
}
