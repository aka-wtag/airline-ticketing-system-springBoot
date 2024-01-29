package com.ats.model.flight;

import com.ats.model.airline.Airline;
import com.ats.model.booking.Booking;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Flight implements Serializable {
    @Id
    @GeneratedValue
    public int flightId;
    @Column(nullable = false)
    private String departureDate;
    @Column(nullable = false)
    private String departureTime;
    @Column(nullable = false)
    private String arrivalDate;
    @Column(nullable = false)
    private String arrivalTime;
    @Column(nullable = false)
    private String departureLocation;
    @Column(nullable = false)
    private String arrivalLocation;
    @Column(nullable = false)
    private int remainingSeats;
    @Column(nullable = false)
    private double fare;

    @OneToMany(mappedBy = "flight", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Booking> booking;

    @ManyToOne
    private Airline airline;

    }