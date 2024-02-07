package com.ats.model.airline;

import com.ats.model.flight.Flight;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Airline implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int airlineId;
    private String airlineModel;
    private String airlineName;
    private int numberOfSeats;

    @OneToMany(mappedBy = "airline",  cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Flight> flights;
}
