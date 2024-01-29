package com.ats.model.airline;

import com.ats.model.flight.Flight;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
public class Airline implements Serializable {
    @Id
    private String airlineId;
    private String airlineName;
    private int numberOfSeats;

    @OneToMany(mappedBy = "airline",  cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Flight> flights;
}
