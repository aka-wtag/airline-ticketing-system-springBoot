package com.ats.model.booking;

import com.ats.model.flight.Flight;
import com.ats.model.user.Passenger;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Booking implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int bookingNumber;
    @Column(nullable = false)
    private String bookingDate;
    @Column(nullable = false)
    private int bookedSeats;
    @Column(nullable = false)
    private double bookingAmount;

    @ManyToOne
    private Passenger passenger;

    @ManyToOne
    private Flight flight;
}
