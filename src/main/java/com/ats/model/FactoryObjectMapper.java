package com.ats.model;

import com.ats.model.airline.Airline;
import com.ats.model.airline.CreateAirlineDto;
import com.ats.model.booking.Booking;
import com.ats.model.booking.CreateBookingDto;
import com.ats.model.booking.BookingOutputDto;
import com.ats.model.flight.Flight;
import com.ats.model.flight.CreateFlightDto;
import com.ats.model.user.Passenger;
import com.ats.model.user.CreatePassengerDto;
import com.ats.model.user.PassengerOutputDto;

import java.time.LocalDateTime;
import java.util.Objects;

public class FactoryObjectMapper {
    public static Passenger convertPassengerInputToModel(CreatePassengerDto createPassengerDto){
        Passenger passenger = new Passenger();
        passenger.setUserFullName(createPassengerDto.getUserFullName());
        passenger.setUserEmail(createPassengerDto.getUserEmail());
        passenger.setUserPassword(createPassengerDto.getUserPassword());
        passenger.setUserContact(createPassengerDto.getUserContact());
        passenger.setPassengerPassport(createPassengerDto.getPassengerPassport());
        return passenger;
    }

    public static PassengerOutputDto convertPassengerEntityToPassengerOutput(Passenger passenger){
        PassengerOutputDto passengerOutputDto = new PassengerOutputDto();
        passengerOutputDto.setUserId(passenger.getUserId());
        passengerOutputDto.setUserFullName(passenger.getUserFullName());
        passengerOutputDto.setUserEmail(passenger.getUserEmail());
        passengerOutputDto.setUserContact(passenger.getUserContact());
        passengerOutputDto.setPassengerPassport(passenger.getPassengerPassport());

        return passengerOutputDto;
    }

    public static Airline convertAirlineInputToModel(CreateAirlineDto createAirlineDto, String lastAirlineModel){
        Airline airline = new Airline();
        airline.setAirlineModel(generateAirlineModel(createAirlineDto.getAirlineName(), lastAirlineModel));
        airline.setAirlineName(createAirlineDto.getAirlineName());
        airline.setNumberOfSeats(createAirlineDto.getNumberOfSeats());
        return airline;
    }

    public static String generateAirlineModel(String airlineName, String lastAirlineModel) {
      int airlineModelId = 101;
      if(Objects.nonNull(lastAirlineModel)) {
        airlineModelId = Integer.parseInt(lastAirlineModel.split("-")[1]) + 1;
      }
      return airlineName.substring(0, 2).toUpperCase() + "-" + airlineModelId;
    }

    public static Flight convertFlightInputToModel(CreateFlightDto createFlightDto, Airline airline){
        Flight flight = new Flight();

        flight.setRemainingSeats(airline.getNumberOfSeats());
        flight.setFare(createFlightDto.getFare());
        flight.setAirline(airline);
        flight.setDepartureDate(createFlightDto.getDepartureDate());
        flight.setArrivalDate(createFlightDto.getArrivalDate());
        flight.setDepartureLocation(createFlightDto.getDepartureLocation());
        flight.setArrivalLocation(createFlightDto.getArrivalLocation());
        flight.setDepartureTime(createFlightDto.getDepartureTime());
        flight.setArrivalTime(createFlightDto.getArrivalTime());

        return flight;
    }

    public static Booking convertBookingInputToModel(CreateBookingDto createBookingDto, Passenger passenger, Flight flight) {
        Booking booking = new Booking();

        booking.setBookingDate(LocalDateTime.now());
        booking.setBookedSeats(createBookingDto.getBookedSeats());
        booking.setBookingAmount(flight.getFare()*createBookingDto.getBookedSeats());
        booking.setPassenger(passenger);
        booking.setFlight(flight);

        return booking;
    }

    public static BookingOutputDto convertModelToBookingOutput(Booking booking) {
        BookingOutputDto bookingOutputDto = new BookingOutputDto();

        bookingOutputDto.setBookingNumber(booking.getBookingNumber());
        bookingOutputDto.setBookingDate(booking.getBookingDate());
        bookingOutputDto.setBookedSeats(booking.getBookedSeats());
        bookingOutputDto.setBookingAmount(booking.getBookingAmount());

        bookingOutputDto.setPassenger(FactoryObjectMapper.convertPassengerEntityToPassengerOutput(booking.getPassenger()));
        bookingOutputDto.setFlight(booking.getFlight());

        return bookingOutputDto;
    }
}
