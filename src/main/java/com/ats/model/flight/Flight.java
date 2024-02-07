package com.ats.model.flight;

import com.ats.model.airline.Airline;
import com.ats.model.booking.Booking;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Getter
@Setter
public class Flight implements Serializable {
    @Id
    @GeneratedValue
    public int flightId;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate departureDate;
    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime departureTime;
    @Column(nullable = false)
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate arrivalDate;
    @Column(nullable = false)
    @JsonFormat(pattern = "HH:mm")
    private LocalTime arrivalTime;
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